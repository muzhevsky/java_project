package org.muzhevsky.authentication.repos;

import org.muzhevsky.authentication.models.UserModel;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<UserModel, Integer> {

    @Modifying
    @Query("INSERT INTO USERS(account_id) VALUES(:id)")
    void createNewUserById(@Param("id") int id);
}
