package org.muzhevsky.authorization.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@AllArgsConstructor
@Table("token_pairs")
@ToString
public class TokenPair {
    @Column("access_token")
    @Id
    private String accessToken;
    @Column("refresh_token")
    private String refreshToken;
}
