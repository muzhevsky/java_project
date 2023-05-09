package org.muzhevsky.home.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import org.muzhevsky.authorization.exceptions.ExpiredTokenException;
import org.muzhevsky.authorization.exceptions.TokenNotFoundException;
import org.muzhevsky.authorization.services.AuthorizationService;
import org.muzhevsky.authorization.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @Autowired
    @Qualifier("authorizationService")
    AuthorizationService authorizationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/home")
    public ResponseEntity<String> home(@RequestHeader("accessToken") String accessToken){
        try {
            var tokens = authorizationService.getTokenData(accessToken);
            if (tokens.isExpired()) throw new ExpiredTokenException();

            var role =  authorizationService.getUserRole(accessToken).name();
            return new ResponseEntity<>(role, HttpStatus.OK);
        }
        catch (ExpiredTokenException ex){
            System.out.println("expired");
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
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
