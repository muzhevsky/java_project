package muzhevsky.org.auth.services;

import lombok.Getter;
import muzhevsky.org.auth.dtos.TokenPairDto;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import muzhevsky.org.auth.dtos.AuthenticationDto;
import muzhevsky.org.auth.dtos.SignInDto;

import java.util.List;

@Service
public class SignInService {
    @Autowired
    private KeycloakAdminService keycloakAdminService;

    @Autowired
    private AuthorizationService authorizationService;

    @Value("${keycloak.auth-server-url}")
    @Getter
    private String url;
    @Value("${keycloak.resource}")
    @Getter
    private String client;
    @Value("${keycloak.credentials.secret}")
    @Getter
    private String secret;
    @Value("${keycloak.realm}")
    @Getter
    private String realm;

    public AuthenticationDto signIn(SignInDto form) {
        var userKeycloak = KeycloakBuilder.builder()
                .serverUrl(url)
                .realm(realm)
                .clientId(client)
                .clientSecret(secret)
                .username(form.getUsername())
                .password(form.getPassword())
                .grantType(OAuth2Constants.PASSWORD)
                .build();
        var tokens = userKeycloak.tokenManager().getAccessToken();
        var accessToken = tokens.getToken();
        var refreshToken = tokens.getRefreshToken();

        TokenPairDto tokenPairDto = new TokenPairDto(accessToken, refreshToken);
        List<String> roles = null;

        try {
            roles = authorizationService.getRoles(accessToken);
        } catch (Exception ex) {
            System.out.println(ex);
        }


        return new AuthenticationDto(tokenPairDto, roles);
    }
}
