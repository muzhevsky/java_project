package org.muzhevsky.auth.dtos;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Map<String, List<String>> attributes;
}
