package org.muzhevsky.authorization.models;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.muzhevsky.authentication.dtos.AccountCreationForm;
import org.muzhevsky.authentication.exceptions.EncryptionException;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("accounts")
@ToString
@Getter
public class AccountModel {
    @Id
    private Integer id;
    private String email;
    private byte[] password;
    private byte[] salt;
    private int roleId;
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
