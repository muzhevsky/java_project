package org.muzhevsky.authentication.controllers;

import org.muzhevsky.authentication.dtos.CompanySignUpForm;
import org.muzhevsky.authentication.dtos.UserCreationForm;
import org.muzhevsky.authentication.services.signup.CompanySignUpService;
import org.muzhevsky.authentication.services.signup.UserSignUpService;
import org.muzhevsky.authorization.models.TokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    @Autowired
    @Qualifier("userSignupService")
    UserSignUpService userSignUpService;
    @Autowired
    @Qualifier("companySignupService")
    CompanySignUpService companySignUpService;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup/user")
    public ResponseEntity<TokenPair> signUp(@RequestBody UserCreationForm form){
        try{
            var response = userSignUpService.signUp(form);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup/company")
    public ResponseEntity<TokenPair> signUp(@RequestBody CompanySignUpForm form){
        try{
            var response = companySignUpService.signUp(form);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }
}
