package org.muzhevsky.authentication.models;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
@ToString
@AllArgsConstructor
public class UserModel implements Persistable<Integer> {
    @Id
    @Column("account_id")
    private Integer accountId;
    @Column("username")
    private String username;

    public void init(Integer accountId){
        this.accountId = accountId;
    }

    @Override
    public Integer getId() {
        return accountId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
