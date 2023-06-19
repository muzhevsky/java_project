package muzhevsky.org.auth.services;

import muzhevsky.org.auth.enums.Role;
import muzhevsky.org.auth.exceptions.UserAlreadyExistsException;
import muzhevsky.org.auth.dtos.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignUpService {
    @Autowired
    AccountCreationService accountCreationService;
    @Autowired
    KeycloakAdminService keycloakAdminService;

    public void signUp(SignUpDto form, Role role) throws InternalError, UserAlreadyExistsException {
        var id = accountCreationService.createAccount(form);
        var adminKeycloak = keycloakAdminService.getAdminKeycloak();
        var realm = keycloakAdminService.getRealmId();

        var realmResource = adminKeycloak.realm(realm);
        var usersResource = realmResource.users();

        var roles = realmResource.roles().list();
        roles.forEach(item -> {
            System.out.print(item.getId() + " ");
            System.out.println(item.getName());
        });
        var accountRole = roles.stream().filter(r -> r.getName().equals(role.get())).findFirst().get();
        usersResource.get(id).roles().realmLevel().add(List.of(accountRole));
    }
}
