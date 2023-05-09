package org.muzhevsky.authentication.models;

import lombok.Getter;
import org.muzhevsky.authentication.dtos.CompanySignUpForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table("companies")
public class CompanyModel {
    @Id
    @Column("id")
    public Integer id;
    @Getter
    @Column("company_name")
    private String companyName;
    @Getter
    @Column("owner_surname")
    private String ownerSurname;
    @Getter
    @Column("owner_name")
    private String ownerName;
    @Getter
    @Column("owner_patronymic")
    @Embedded.Nullable
    private String ownerPatronymic;
    @Getter
    @Column("phone_number")
    private String phoneNumber;
    @Getter
    @Column("inn_file")
    private String inn_file;

    public CompanyModel(CompanySignUpForm data){
        this.companyName = data.getCompanyName();
        this.ownerSurname = data.getOwnerSurname();
        this.ownerName = data.getOwnerName();
        this.ownerPatronymic = data.getOwnerPatronymic();
        this.phoneNumber = data.getPhoneNumber();
        this.inn_file = data.getInnFileJson();
    }
}
