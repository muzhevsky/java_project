package org.muzhevsky.signup.dtos;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignInForm {
    private String email;
    private String password;
}
