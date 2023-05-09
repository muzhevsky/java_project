package org.muzhevsky.authentication.repos;

import org.muzhevsky.authentication.models.AccountRoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("accountRoleRepository")
public interface AccountRoleRepository extends CrudRepository<AccountRoleModel, Integer> {
    public AccountRoleModel findAccountRoleModelByName(String name);
}
