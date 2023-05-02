package org.muzhevsky.authorization.services;

import org.muzhevsky.authorization.exceptions.TokenNotFoundException;
import org.muzhevsky.signup.exceptions.UserNotFoundException;
import org.muzhevsky.signup.repos.AccountRepository;
import org.muzhevsky.signup.repos.AccountRoleRepository;
import org.muzhevsky.authorization.enums.Role;
import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.authorization.repos.TokenRepository;
import org.muzhevsky.config.AuthConfig;
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
    AuthConfig config;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountRoleRepository accountRoleRepository;

    public TokenPair getTokenPair(int userId){
        var configModel = config.getModel();
        var accessToken = jwtTokenService.generateAccessToken(userId, configModel.getAccessTokenLifetime());
        var refreshToken = jwtTokenService.generateRefreshToken(configModel.getRefreshTokenLifetime());

        var tokenModel = new TokenPair(accessToken, refreshToken);
        tokenRepository.insertToken(tokenModel.getAccessToken(), tokenModel.getRefreshToken());

        return tokenModel;
    }

    public Role checkToken(String accessToken) throws TokenNotFoundException, UserNotFoundException {
        var tokenPairModel = tokenRepository.findById(accessToken);
        if (tokenPairModel.isEmpty()){
            throw new TokenNotFoundException();
        }
        var tokenData = jwtTokenService.decodeToken(tokenPairModel.get().getAccessToken());
        var accountModel = accountRepository.findById(tokenData.getUserId());
        if (accountModel.isEmpty()){
            throw new UserNotFoundException("user with this id wasn't found");
        }
        var roleData = accountRoleRepository.findById(accountModel.get().getRoleId());
        return Role.valueOf(roleData.get().getName());
    }
}
