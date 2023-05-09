package org.muzhevsky.authorization.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwsHeader;
import org.muzhevsky.authorization.exceptions.ExpiredTokenException;
import org.muzhevsky.authorization.exceptions.TokenNotFoundException;
import org.muzhevsky.authorization.exceptions.UserNotFoundException;
import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.authorization.repos.TokenRepository;
import org.muzhevsky.authorization.services.AuthorizationService;
import org.muzhevsky.authorization.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorizationController {

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    @Qualifier("authorizationService")
    AuthorizationService authorizationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/refresh")
    public ResponseEntity<TokenPair> refreshTokenPair(@RequestBody String token){
        try{
            var tokenPair = refreshTokenService.refreshAuthorizationTokens(token);
            System.out.println(tokenPair);
            return new ResponseEntity<>(tokenPair, HttpStatus.OK);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/authorize")
    public ResponseEntity<String> getRole(@RequestHeader("accessToken") String accessToken){
        try {
            var tokens = authorizationService.getTokenData(accessToken);
            if (tokens.isExpired()) throw new ExpiredTokenException();

            var role =  authorizationService.getUserRole(accessToken).name();
            return new ResponseEntity<>(role, HttpStatus.OK);
        }
        catch (TokenNotFoundException | UserNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("guest", HttpStatus.OK);
        }
        catch (ExpiredTokenException ex){
            System.out.println("expired");
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }
    }
}
