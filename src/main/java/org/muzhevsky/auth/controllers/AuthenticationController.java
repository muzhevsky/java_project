package org.muzhevsky.auth.controllers;

import org.muzhevsky.auth.dtos.AccountDto;
import org.muzhevsky.auth.dtos.AuthenticationDto;
import org.muzhevsky.auth.dtos.SignInDto;
import org.muzhevsky.auth.exceptions.UserAlreadyExistsException;
import org.muzhevsky.auth.services.AuthorizationService;
import org.muzhevsky.auth.services.UserCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationController {
    @Autowired
    UserCreationService userCreationService;
    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/signup/user")
    public String getUserSignUp() {
        return "userSignup";
    }
    @GetMapping("/signup/company")
    public String getCompanySignUp(){
        return "companySignUp";
    }
    @GetMapping("/signin")
    public String getSignIn() {
        return "signin";
    }

    @PostMapping("/api/signup")
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestBody AccountDto form) {
        try {
            userCreationService.createUser(form);
        } catch (UserAlreadyExistsException ex) {
            return new ResponseEntity<>("error", HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>("unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/signin")
    public ResponseEntity<AuthenticationDto> signIn(@RequestBody SignInDto form, HttpServletResponse response){
        try{
            var authorizationDto = authorizationService.signIn(form);
            return new ResponseEntity<>(authorizationDto, HttpStatus.OK);
        }
        catch (Exception ex){
            System.out.println(ex);
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
