package org.muzhevsky.signup.repos;

import org.muzhevsky.signup.models.AccountRoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("accountRoleRepository")
public interface AccountRoleRepository extends CrudRepository<AccountRoleModel, Integer> {
    public AccountRoleModel findAccountRoleModelByName(String name);
}
