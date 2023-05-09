package org.muzhevsky.authentication.services.signup;

import org.muzhevsky.authentication.dtos.CompanySignUpForm;
import org.muzhevsky.authentication.models.AccountModel;
import org.muzhevsky.authentication.models.CompanyModel;
import org.muzhevsky.authentication.repos.AccountRoleRepository;
import org.muzhevsky.authentication.repos.CompanyRepository;
import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.authorization.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("companySignupService")
public class CompanySignUpService {

    @Autowired
    @Qualifier("createAccountService")
    private CreateAccountService createAccountService;
    @Autowired
    @Qualifier("authorizationService")
    private AuthorizationService authorizationService;

    @Autowired
    @Qualifier("companyRepository")
    private CompanyRepository companyRepository;
    @Autowired
    @Qualifier("accountRoleRepository")
    private AccountRoleRepository accountRoleRepository;

    public TokenPair signUp(CompanySignUpForm companySignUpForm) {
        var accountModel = new AccountModel();

        var companyRoleId = accountRoleRepository.findAccountRoleModelByName("company").getId();
        accountModel.init(companyRoleId, companySignUpForm.getAccountCreationForm());
        var companyModel = new CompanyModel(companySignUpForm);

        accountModel = createAccountService.createAccount(accountModel);
        companyModel.id = accountModel.getId();
        companyRepository.insertCompany(companyModel.id, companyModel.getCompanyName(),
                                        companyModel.getOwnerSurname(), companyModel.getOwnerName(),
                                        companyModel.getOwnerPatronymic(), companyModel.getPhoneNumber());

        return authorizationService.getTokenPair(companyModel.id);
    }
}
