package org.muzhevsky.home.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import org.muzhevsky.authorization.exceptions.TokenNotFoundException;
import org.muzhevsky.authorization.services.AuthorizationService;
import org.muzhevsky.signup.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    @Qualifier("authorizationService")
    AuthorizationService authorizationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/home")
    public ResponseEntity<String> home(@RequestBody String accessToken){
        try {
            return new ResponseEntity<>(authorizationService.checkToken(accessToken).name(), HttpStatus.OK);
        }
        catch (TokenNotFoundException | UserNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("guest", HttpStatus.OK);
        }
        catch (ExpiredJwtException ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }
    }
}
