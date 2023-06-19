package muzhevsky.org.auth.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import muzhevsky.org.auth.dtos.AccountDto;
import muzhevsky.org.auth.enums.Role;
import org.keycloak.OAuth2Constants;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import muzhevsky.org.auth.dtos.TokenPairDto;
import muzhevsky.org.auth.dtos.AccountIdentityDto;
import muzhevsky.org.auth.exceptions.TokenExpiredException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorizationService {
    @Autowired
    private KeycloakAdminService keycloakAdminService;

    private final List<String> roleNames = List.of(Role.USER.get(), Role.COMPANY.get(), Role.ADMIN.get());
    private HttpClient client;
    private ObjectMapper mapper;

    @PostConstruct
    public void init() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public List<String> getRoles(String accessToken) throws UsernameNotFoundException, TokenExpiredException, VerificationException {
        var adminKeycloak = keycloakAdminService.getAdminKeycloak();

        var verifier = TokenVerifier.create(accessToken, AccessToken.class);
        if (verifier.getToken().isExpired())
             throw new TokenExpiredException();

        AccessToken parsedToken = verifier.getToken();

        var realm = adminKeycloak.realm(keycloakAdminService.getRealmId());
        var user = realm.users().searchByUsername(parsedToken.getPreferredUsername(), true);
        if (user == null)
            throw new UsernameNotFoundException("user not found");

        var userRoles = new ArrayList<String>();
        roleNames.forEach(roleName -> {
            var users = realm.roles().get(roleName).getUserMembers();
            var filtered = users.stream().filter(userRepresentation -> userRepresentation.getUsername().equals(parsedToken.getPreferredUsername()));
            var userFound = filtered.findFirst();
            if (userFound.isPresent()) userRoles.add(roleName);
        });

        return userRoles;

    }

    public AccountIdentityDto getUserIdentity(String accessToken) throws UsernameNotFoundException, TokenExpiredException {
        try {
            var verifier = TokenVerifier.create(accessToken, AccessToken.class);
            if (verifier.getToken().isExpired())
                throw new TokenExpiredException();
            var tokenRepresentation = verifier.getToken();

            var userIdentity = new AccountIdentityDto(tokenRepresentation.getSubject(),
                    tokenRepresentation.getPreferredUsername(),
                    tokenRepresentation.getEmail());
            return userIdentity;
        } catch (VerificationException e) {
            return null;
        }
    }

    public AccountDto getAccountDtoByToken(String accessToken) throws UsernameNotFoundException, TokenExpiredException{
        return getAccountDtoById(getUserIdentity(accessToken).getId());
    }

    public AccountDto getAccountDtoById(String accountId) throws UsernameNotFoundException{
        var adminKeycloak = keycloakAdminService.getAdminKeycloak();
        var user = adminKeycloak.realm(keycloakAdminService.getRealmId()).users().get(accountId);

        return new AccountDto(user.toRepresentation());
    }

    @SneakyThrows
    public TokenPairDto refreshToken(String refreshToken) {
        var properties = new HashMap<String, String>();

        properties.put("client_id", keycloakAdminService.getClientId());
        properties.put("grant_type", OAuth2Constants.REFRESH_TOKEN);
        properties.put("refresh_token", refreshToken);
        properties.put("client_secret", keycloakAdminService.getClientSecret());

        var request = HttpRequest.newBuilder()
                .uri(new URI(keycloakAdminService.getServerUrl() +
                        "/realms/" +
                        keycloakAdminService.getRealmId() +
                        "/protocol/openid-connect/token"))
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .POST(getParamsUrlEncoded(properties))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var tokenRepresentation = mapper.readValue(response.body(), AccessTokenResponse.class);

        return new TokenPairDto(tokenRepresentation.getToken(), tokenRepresentation.getRefreshToken());
    }

    private HttpRequest.BodyPublisher getParamsUrlEncoded(Map<String, String> parameters) {
        String urlEncoded = parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        return HttpRequest.BodyPublishers.ofString(urlEncoded);
    }

    public void onSigninFailed(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.sendRedirect("/signin");
    }
}
