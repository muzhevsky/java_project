package org.muzhevsky.authorization.services;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import org.muzhevsky.authorization.exceptions.TokenNotFoundException;
import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.authorization.repos.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    AuthorizationService authorizationService;

    @SneakyThrows
    public TokenPair refreshAuthorizationTokens(String refreshToken){
        var tokenPair = tokenRepository.findFirstByRefreshToken(refreshToken);

        if (tokenPair.isEmpty()) throw new TokenNotFoundException();

        int userId = -1;

        try{
            userId = authorizationService.getTokenData(tokenPair.get().getAccessToken()).getAccountId();
        }
        catch (ExpiredJwtException ex){
            userId = ex.getClaims().get("id", Integer.class);
        }

        tokenRepository.deleteByRefreshToken(refreshToken);
        return authorizationService.getTokenPair(userId);
    }
}
