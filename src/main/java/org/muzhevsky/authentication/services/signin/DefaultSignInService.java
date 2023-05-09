package org.muzhevsky.authentication.services.signin;

import lombok.SneakyThrows;
import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.authorization.services.AuthorizationService;
import org.muzhevsky.authentication.dtos.SignInForm;
import org.muzhevsky.authentication.exceptions.UserNotFoundException;
import org.muzhevsky.authentication.exceptions.WrongPasswordException;
import org.muzhevsky.authentication.repos.AccountRepository;
import org.muzhevsky.authentication.services.encryption.PasswordEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DefaultSignInService implements SignInService{
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncryptionService encryptionService;
    @Autowired
    @Qualifier("authorizationService")
    private AuthorizationService authorizationService;
    @Override
    @SneakyThrows
    public TokenPair signIn(SignInForm signInForm) {
        var accountData = accountRepository.findFirstByEmail(signInForm.getEmail());
        if (accountData == null)
            throw new UserNotFoundException("user not found");

        var inputPassword = signInForm.getPassword().getBytes();
        var passwordBytes = encryptionService.encryptPassword(
                inputPassword, accountData.getSalt());

        var dbPass = accountData.getPassword();
        if (!Arrays.equals(passwordBytes, dbPass)) {
            throw new WrongPasswordException();
        }

        return authorizationService.getTokenPair(accountData.getId());
    }
}