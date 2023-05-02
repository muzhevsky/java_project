package org.muzhevsky.signup.services.signup;

import org.muzhevsky.signup.models.AccountModel;

public interface CreateAccountService {
    AccountModel createAccount(AccountModel accountModel);
}
