package org.muzhevsky.authorization.services;

import io.jsonwebtoken.ExpiredJwtException;
import org.muzhevsky.authorization.config.AuthorizationConfig;
import org.muzhevsky.authorization.dtos.TokenData;
import org.muzhevsky.authorization.exceptions.TokenNotFoundException;
import org.muzhevsky.authorization.repos.AccountRepository;
import org.muzhevsky.authorization.repos.AccountRoleRepository;
import org.muzhevsky.authorization.enums.Role;
import org.muzhevsky.authorization.exceptions.UserNotFoundException;
import org.muzhevsky.authorization.models.AccountModel;
import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.authorization.repos.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    @Autowired
    @Qualifier("tokenService")
    TokenService jwtTokenService;

    @Autowired
    @Qualifier("authConfig")
    AuthorizationConfig config;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    @Qualifier("AuthorizationAccountRepository")
    AccountRepository accountRepository;

    @Autowired
    @Qualifier("AuthorizationAccountRoleRepository")
    AccountRoleRepository accountRoleRepository;

    public TokenPair getTokenPair(int userId){
        var configModel = config.getModel();
        var accessToken = jwtTokenService.generateAccessToken(userId, configModel.getAccessTokenLifetime());
        var refreshToken = jwtTokenService.generateRefreshToken(configModel.getRefreshTokenLifetime());

        var tokenModel = new TokenPair(accessToken, refreshToken);
        tokenRepository.insertToken(tokenModel.getAccessToken(), tokenModel.getRefreshToken());

        return tokenModel;
    }

    public TokenData getTokenData(String accessToken) throws TokenNotFoundException{
        var tokenPairModel = tokenRepository.findById(accessToken);
        if (tokenPairModel.isEmpty()){
            throw new TokenNotFoundException();
        }

        TokenData tokenData;
        try{
            tokenData = jwtTokenService.decodeToken(tokenPairModel.get().getAccessToken());
        }
        catch (ExpiredJwtException ex){
            tokenData = new TokenData(ex.getClaims().get("id", Integer.class), true);
        }

        return tokenData;
    }


    private AccountModel getAccount(String accessToken)throws TokenNotFoundException, UserNotFoundException{
        var userId = getTokenData(accessToken).getAccountId();
        var accountModel = accountRepository.findById(userId);

        if (accountModel.isEmpty()){
            throw new UserNotFoundException("user with this id wasn't found");
        }

        return accountModel.get();
    }


    public Role getUserRole(String accessToken) throws TokenNotFoundException, UserNotFoundException{
        var accountModel = getAccount(accessToken);
        var roleData = accountRoleRepository.findById(accountModel.getRoleId());
        return Role.valueOf(roleData.get().getName());
    }
}
