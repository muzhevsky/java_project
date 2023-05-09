package org.muzhevsky.authentication.models;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
@ToString
@AllArgsConstructor
public class UserModel {
    @Column("id")
    public int id;
}
