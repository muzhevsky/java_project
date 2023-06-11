package org.muzhevsky.auth.services;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.muzhevsky.auth.dtos.AccountDto;
import org.muzhevsky.auth.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountCreationService {
    @Autowired
    KeycloakAdminService keycloakAdminService;

    public String createAccount(AccountDto account) throws UserAlreadyExistsException, InternalError {
        var realm = keycloakAdminService.getRealm();
        var adminKeycloak = keycloakAdminService.getAdminKeycloak();

        var credentials = new CredentialRepresentation();
        credentials.setType("password");
        credentials.setValue(account.getPassword());

        var userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(account.getUsername());
        userRepresentation.setEmail(account.getEmail());
        userRepresentation.setFirstName(account.getFirstName());
        userRepresentation.setLastName(account.getLastName());
        userRepresentation.setCredentials(Collections.singletonList(credentials));
        userRepresentation.setAttributes(account.getAttributes());
        userRepresentation.setEnabled(true);

        var usersResource = adminKeycloak.realm(realm).users();

        var response = usersResource.create(userRepresentation);
        if (response.getStatus() <= 299) {
            List<UserRepresentation> userList = usersResource.searchByUsername(account.getUsername(), true);
            if (userList.size() == 0) throw new InternalError();
            return userList.get(0).getId();
        }

        switch (response.getStatus()){
            case 409:
                throw new UserAlreadyExistsException();
            default:
                throw new InternalError();
        }
    }
}
