package org.muzhevsky.authentication.controllers;

import org.muzhevsky.authentication.dtos.ShortAccountData;
import org.muzhevsky.authentication.repos.AccountRepository;
import org.muzhevsky.authentication.repos.CompanyRepository;
import org.muzhevsky.authentication.repos.UserRepository;
import org.muzhevsky.authorization.exceptions.TokenNotFoundException;
import org.muzhevsky.authorization.exceptions.UserNotFoundException;
import org.muzhevsky.authorization.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/shortaccount")
    public ResponseEntity<ShortAccountData> getShortAccountData(@RequestHeader String accessToken){
        try {
            var role = authorizationService.getUserRole(accessToken);
            var id = authorizationService.getTokenData(accessToken).getAccountId();
            return new ResponseEntity<>(accountRepository.getShortUserData(id), HttpStatus.OK);
        } catch (TokenNotFoundException | UserNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
