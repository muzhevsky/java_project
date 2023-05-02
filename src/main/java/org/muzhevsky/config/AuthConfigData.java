package org.muzhevsky.config;

import lombok.Getter;

@Getter
public class AuthConfigData {
    String key;
    int accessTokenLifetime;
    int refreshTokenLifetime;
}
