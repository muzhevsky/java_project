package org.muzhevsky.authentication.models;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("account_roles")
@Getter
public class AccountRoleModel {
    @Id
    private int id;
    private String name;
}
