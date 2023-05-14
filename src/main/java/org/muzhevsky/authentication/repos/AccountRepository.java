package org.muzhevsky.authentication.repos;

import org.muzhevsky.authentication.dtos.ShortAccountData;
import org.muzhevsky.authentication.models.AccountModel;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<AccountModel, Integer> {
    AccountModel findFirstByEmail(String email);

    @Query("select username from short_account_data where account_id = :id")
    ShortAccountData getShortUserData(@Param("id") Integer id);
}
