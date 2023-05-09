package org.muzhevsky.authentication.dtos;

import lombok.Getter;

@Getter
public class CompanySignUpForm {
     private AccountCreationForm accountCreationForm;
     private String companyName;
     private String ownerSurname;
     private String ownerName;
     private String ownerPatronymic;
     private String phoneNumber;
     private String innFileJson;
}
