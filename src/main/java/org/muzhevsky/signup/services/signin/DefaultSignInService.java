package org.muzhevsky.signup.services.signin;

import lombok.SneakyThrows;
import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.authorization.services.AuthorizationService;
import org.muzhevsky.signup.dtos.SignInForm;
import org.muzhevsky.signup.exceptions.UserNotFoundException;
import org.muzhevsky.signup.exceptions.WrongPasswordException;
import org.muzhevsky.signup.repos.AccountRepository;
import org.muzhevsky.signup.services.encryption.PasswordEncryptionService;
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
    public TokenPair signIn(SignInForm signInForm) throws UserNotFoundException, WrongPasswordException{
        var accountData = accountRepository.findFirstByEmail(signInForm.getEmail());
        if (accountData == null)
            throw new UserNotFoundException("user not found");

        var inputPassword = signInForm.getPassword().getBytes();
        var passwordBytes = encryptionService.encryptPassword(
                inputPassword, accountData.getSalt());


        if (!Arrays.equals(passwordBytes,accountData.getPassword())) {
            throw new WrongPasswordException();
        }

        return authorizationService.getTokenPair(accountData.getId());
    }
}
