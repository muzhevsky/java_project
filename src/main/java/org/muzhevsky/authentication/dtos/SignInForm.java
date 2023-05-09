package org.muzhevsky.authentication.dtos;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignInForm {
    private String email;
    private String password;
}
