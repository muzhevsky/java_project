package org.muzhevsky.authentication.models;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("account_roles")
public class AccountRoleModel {
    @Getter
    @Id
    private int id;
    @Getter
    private String name;
}
