package org.muzhevsky.signup.models;

import lombok.*;
import org.muzhevsky.signup.dtos.AccountCreationForm;
import org.muzhevsky.signup.exceptions.EncryptionException;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("accounts")
@ToString
public class AccountModel {
    @Id
    @Column("id")
    @Getter
    private Integer id;

    @Column("email")
    @Getter
    private String email;

    @Column("password")
    @Getter
    private byte[] password;

    @Column("salt")
    @Getter
    private byte[] salt;

    @Column("role_id")
    @Getter
    private int roleId;

    @Column("is_subscriber")
    private String isSubscriber;

    @SneakyThrows
    public void setPassword(byte[] newPassword){
        if (newPassword == null || newPassword.length == 0) throw new EncryptionException("encryption failed");
        password = newPassword;
    }

    @SneakyThrows
    public void setSalt(byte[] newSalt){
        if (newSalt == null || newSalt.length == 0) throw new EncryptionException("encryption failed");
        salt = newSalt;
    }

    public void init(int roleId, AccountCreationForm data){
        this.roleId = roleId;
        this.email = data.getEmail();
        this.password = data.getPassword().getBytes();
        this.isSubscriber = data.getIsSubscriber();
    }
}
