package muzhevsky.org.auth.services;

import lombok.Getter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakAdminService {
    @Value("${keycloak.auth-server-url}")
    @Getter
    private String serverUrl;
    @Value("${keycloak.resource}")
    @Getter
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    @Getter
    private String clientSecret;
    @Value("${keycloak.realm}")
    @Getter
    private String realmId;

    public Keycloak getAdminKeycloak() {
        try {
            var builder = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realmId)
                    .clientId(clientId)
                    .grantType("client_credentials")
                    .clientSecret(clientSecret)
                    .build();
            return builder;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }
}
