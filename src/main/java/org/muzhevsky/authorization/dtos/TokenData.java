package org.muzhevsky.authorization.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenData {
    private Integer accountId;
    private boolean expired;
}
