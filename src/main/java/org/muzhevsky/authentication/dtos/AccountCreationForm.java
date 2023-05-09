package org.muzhevsky.authentication.dtos;


import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AccountCreationForm {
     private String email;
     private String password;
     private String isSubscriber;
}
