package org.muzhevsky.auth.services;

import org.keycloak.representations.account.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.muzhevsky.auth.dtos.AccountDto;
import org.muzhevsky.auth.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCreationService {
    @Autowired
    AccountCreationService accountCreationService;
    @Autowired
    KeycloakAdminService keycloakAdminService;

    public void createUser(AccountDto form) throws InternalError, UserAlreadyExistsException {
        var id = accountCreationService.createAccount(form);
        var adminKeycloak = keycloakAdminService.getAdminKeycloak();
        var realm = keycloakAdminService.getRealm();

        var realmResource = adminKeycloak.realm(realm);
        var usersResource = realmResource.users();

        var roles = realmResource.roles().list();
        roles.forEach(item -> {System.out.print(item.getId()+" ");
        System.out.println(item.getName());});
        var role = roles.stream().filter(r->r.getName().equals("user")).findFirst().get();
        usersResource.get(id).roles().realmLevel().add(List.of(role));
    }
}
