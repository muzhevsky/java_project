package org.muzhevsky.signup.services.signup;

import org.muzhevsky.signup.dtos.UserCreationForm;
import org.muzhevsky.signup.models.AccountModel;
import org.muzhevsky.signup.models.UserModel;
import org.muzhevsky.signup.repos.AccountRoleRepository;
import org.muzhevsky.signup.repos.UserRepository;
import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.authorization.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userSignupService")
public class UserSignUpService{

    @Autowired
    @Qualifier("createAccountService")
    private CreateAccountService createAccountService;
    @Autowired
    @Qualifier("authorizationService")
    private AuthorizationService authorizationService;


    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    @Autowired
    @Qualifier("accountRoleRepository")
    private AccountRoleRepository accountRoleRepository;

    public TokenPair signUp(UserCreationForm form) {
        var accountModel = new AccountModel();

        var roleId = accountRoleRepository.findAccountRoleModelByName("user").getId();
        accountModel.init(roleId, form.getAccountCreationForm());

        accountModel = createAccountService.createAccount(accountModel);
        var userModel = new UserModel(accountModel.getId());
        userRepository.createNewUserById(userModel.id);

        return authorizationService.getTokenPair(userModel.id);
    }
}
