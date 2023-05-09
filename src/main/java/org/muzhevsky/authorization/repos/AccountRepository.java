package org.muzhevsky.authorization.repos;

import org.muzhevsky.authorization.models.AccountModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("AuthorizationAccountRepository")
public interface AccountRepository extends CrudRepository<AccountModel, Integer> {
    AccountModel findFirstByEmail(String email);
}
