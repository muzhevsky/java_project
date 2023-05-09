package org.muzhevsky.authorization.repos;

import org.muzhevsky.authentication.models.AccountRoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("AuthorizationAccountRoleRepository")
public interface AccountRoleRepository extends CrudRepository<AccountRoleModel, Integer> {
    AccountRoleModel findAccountRoleModelByName(String name);
}
