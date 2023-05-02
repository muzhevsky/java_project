package org.muzhevsky.authorization.services;

import org.muzhevsky.authorization.dtos.TokenData;

public interface TokenService {
    String generateAccessToken(int id, int expirationTimeSeconds);
    String generateRefreshToken(int expirationTimeSeconds);
    TokenData decodeToken(String token);
}
