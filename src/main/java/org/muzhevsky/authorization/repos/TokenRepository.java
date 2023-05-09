package org.muzhevsky.authorization.repos;

import org.muzhevsky.authorization.models.TokenPair;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<TokenPair, String> {
    @Modifying
    @Query("insert into token_pairs (access_token, refresh_token) values (:accessToken, :refreshToken)")
    void insertToken(@Param("accessToken") String accessToken, @Param("refreshToken") String refreshToken);

    Optional<TokenPair> findFirstByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
