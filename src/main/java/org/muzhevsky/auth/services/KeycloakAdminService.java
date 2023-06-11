package org.muzhevsky.auth.services;

import lombok.Getter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakAdminService{
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

    public Keycloak getAdminKeycloak() {
        try{
           var builder = KeycloakBuilder.builder()
                    .serverUrl(url)
                    .realm(realm)
                    .clientId(client)
                    .grantType("client_credentials")
                    .clientSecret(secret)
                    .build();
           return builder;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            throw ex;
        }
    }
}
