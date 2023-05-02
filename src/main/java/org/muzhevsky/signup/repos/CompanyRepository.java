package org.muzhevsky.signup.repos;

import org.muzhevsky.signup.models.CompanyModel;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("companyRepository")
public interface CompanyRepository extends CrudRepository<CompanyModel, Integer> {
    @Modifying
    @Query("insert into companies(account_id, company_name, owner_surname, owner_name, owner_patronymic, phone_number) " +
            "values(:accountId, :companyName, :ownerSurname, :ownerName, :ownerPatronymic, :phoneNumber)")
    void insertCompany(@Param("accountId") int accountId, @Param("companyName") String companyName,
                       @Param("ownerSurname") String ownerSurname, @Param("ownerName") String ownerName,
                       @Param("ownerPatronymic") String ownerPatronymic, @Param("phoneNumber") String phoneNumber);
}
