package org.muzhevsky.authentication.models;

import lombok.Getter;
import org.muzhevsky.authentication.dtos.CompanySignUpForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table("companies")
@Getter
public class CompanyModel {
    @Id
    public Integer id;
    private String companyName;
    private String ownerSurname;
    private String ownerName;
    private String ownerPatronymic;
    private String phoneNumber;
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
