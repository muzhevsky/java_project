package org.muzhevsky.authentication.repos;

import org.muzhevsky.authentication.models.AccountModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("AccountRepository")
public interface AccountRepository extends CrudRepository<AccountModel, Integer> {
    AccountModel findFirstByEmail(String email);
}
