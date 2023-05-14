package org.muzhevsky.authentication.services.signup;

import lombok.SneakyThrows;
import org.muzhevsky.authentication.exceptions.UserAlreadyExistException;
import org.muzhevsky.authentication.models.AccountModel;
import org.muzhevsky.authentication.repos.AccountRepository;
import org.muzhevsky.authentication.services.encryption.PasswordEncryptionService;
import org.muzhevsky.authentication.services.encryption.SaltGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("createAccountService")
public class DefaultCreateAccountService implements CreateAccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncryptionService encryptionService;
    @Autowired
    private SaltGenerationService saltGenerationService;

    @Override
    @SneakyThrows
    public AccountModel createAccount(AccountModel accountModel) {
        var accounts = accountRepository.findFirstByEmail(accountModel.getEmail());
        if (accounts!=null)
            throw new UserAlreadyExistException("already exist");

        var salt = saltGenerationService.getSalt();
        var password = encryptionService.encryptPassword(accountModel.getPassword(), salt);


        accountModel.setPassword(password);
        accountModel.setSalt(salt);

        return accountModel;
    }
}
