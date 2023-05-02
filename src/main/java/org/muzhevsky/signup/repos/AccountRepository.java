package org.muzhevsky.signup.repos;

import org.muzhevsky.signup.models.AccountModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("AccountRepository")
public interface AccountRepository extends CrudRepository<AccountModel, Integer> {
    AccountModel findFirstByEmail(String email);
}
