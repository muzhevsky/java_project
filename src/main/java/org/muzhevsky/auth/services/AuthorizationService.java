package org.muzhevsky.auth.services;

import lombok.Getter;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.muzhevsky.auth.dtos.AuthenticationDto;
import org.muzhevsky.auth.dtos.SignInDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthorizationService {
    @Autowired
    KeycloakAdminService keycloakAdminService;

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

    public AuthenticationDto signIn(SignInDto form) throws Exception{
        var userKeycloak = KeycloakBuilder.builder()
                .serverUrl(url)
                .realm(realm)
                .clientId(client)
                .clientSecret(secret)
                .username(form.getUsername())
                .password(form.getPassword())
                .grantType(OAuth2Constants.PASSWORD)
                .build();
        var token = userKeycloak.tokenManager().getAccessToken().getToken();
        var adminKeycloak = keycloakAdminService.getAdminKeycloak();

        var users = adminKeycloak.realm(realm)
                .roles()
                .get("user")
                .getUserMembers();

        for (var user : users)
            if (Objects.equals(user.getUsername(), form.getUsername())) return new AuthenticationDto(token, "user");

        var companies = adminKeycloak.realm(realm)
                .roles()
                .get("company")
                .getUserMembers();

        for (var company : companies)
            if (Objects.equals(company.getUsername(), form.getUsername())) return new AuthenticationDto(token, "company");

        throw new Exception();
    }
}
