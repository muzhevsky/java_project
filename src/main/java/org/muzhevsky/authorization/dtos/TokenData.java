package org.muzhevsky.authorization.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class TokenData {
    private Integer userId;
    private Date expireDate;
}
