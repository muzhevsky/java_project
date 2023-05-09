package org.muzhevsky.authorization.config;

import lombok.Getter;

@Getter
public class AuthorizationConfigData {
    private String key;
    private int accessTokenLifetime;
    private int refreshTokenLifetime;
}
