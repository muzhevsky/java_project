package org.muzhevsky.authentication.services.signup;

import org.muzhevsky.authentication.dtos.UserCreationForm;
import org.muzhevsky.authentication.exceptions.UserAlreadyExistException;
import org.muzhevsky.authentication.models.AccountModel;
import org.muzhevsky.authentication.models.UserModel;
import org.muzhevsky.authentication.repos.AccountRepository;
import org.muzhevsky.authentication.repos.AccountRoleRepository;
import org.muzhevsky.authentication.repos.UserRepository;
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
    @Qualifier("accountRepository")
    private AccountRepository accountRepository;
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    @Autowired
    @Qualifier("accountRoleRepository")
    private AccountRoleRepository accountRoleRepository;

    public TokenPair signUp(UserCreationForm form) throws UserAlreadyExistException {
        var accountModel = new AccountModel();

        var roleId = accountRoleRepository.findAccountRoleModelByName("user").getId();
        accountModel.init(roleId, form.getAccountCreationForm());

        accountModel = createAccountService.createAccount(accountModel);
        var userModel = new UserModel(accountModel.getId(), form.getUsername());
        if (userRepository.getUserByUsername(form.getUsername()) != null)
            throw new UserAlreadyExistException("message");



        accountModel = accountRepository.save(accountModel);
        userModel.init(accountModel.getId());
        System.out.println(userRepository.findAll());
        userRepository.save(userModel);

        return authorizationService.getTokenPair(accountModel.getId());
    }
}
