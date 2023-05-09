package org.muzhevsky.authentication.services.signup;

import org.muzhevsky.authentication.models.AccountModel;

public interface CreateAccountService {
    AccountModel createAccount(AccountModel accountModel);
}
