package muzhevsky.org.auth.controllers;

import muzhevsky.org.auth.enums.Role;
import muzhevsky.org.auth.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import muzhevsky.org.auth.dtos.SignUpDto;
import muzhevsky.org.auth.dtos.SignInDto;
import muzhevsky.org.auth.services.SignInService;
import muzhevsky.org.auth.services.SignUpService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AuthenticationController {
    @Autowired
    SignUpService signUpService;
    @Autowired
    SignInService signInService;

    @GetMapping("/signup/user")
    public String getUserSignUp() {
        return "userSignup";
    }

    @GetMapping("/signup/company")
    public String getCompanySignUp() {
        return "companySignUp";
    }

    @GetMapping("/signin")
    public String getSignIn() {
        return "signin";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(HttpServletRequest request, @RequestBody SignUpDto form) {
        try {
            signUpService.signUp(form, form.getAttributes().containsKey("companyName") ? Role.COMPANY : Role.USER);
        } catch (UserAlreadyExistsException ex) {
            return new ResponseEntity<>("error", HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>("unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<List<String>> signIn(@RequestBody SignInDto form) {
        try {
            var authorizationDto = signInService.signIn(form);
            var tokenPair = authorizationDto.getTokenPair();
            var roles = authorizationDto.getRoles();

            var header = new HttpHeaders();
            header.add("Set-Cookie", "accessToken=" + tokenPair.getAccessToken() + "; Max-Age=360000; Path=/");
            header.add("Set-Cookie", "refreshToken=" + tokenPair.getRefreshToken() + "; Max-Age=36000000; Path=/");

            System.out.println(tokenPair.getRefreshToken());
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(roles);
        }
        catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.internalServerError().body(null);
        }
    }
}