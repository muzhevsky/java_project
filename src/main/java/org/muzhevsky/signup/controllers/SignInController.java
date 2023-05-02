package org.muzhevsky.signup.controllers;

import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.signup.dtos.SignInForm;
import org.muzhevsky.signup.exceptions.UserNotFoundException;
import org.muzhevsky.signup.exceptions.WrongPasswordException;
import org.muzhevsky.signup.services.signin.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignInController {

    @Autowired
    SignInService signInService;

    @PostMapping("/signin")
    public ResponseEntity<TokenPair> signIn(@RequestBody SignInForm body){
        try{
            var tokenPair = signInService.signIn(body);
            return new ResponseEntity<>(tokenPair, HttpStatus.OK);
        }
        catch (UserNotFoundException | WrongPasswordException ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
