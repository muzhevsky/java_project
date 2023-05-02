package org.muzhevsky.authorization.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.muzhevsky.authorization.dtos.TokenData;
import org.muzhevsky.config.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component("tokenService")
public class JwtTokenService implements TokenService {
    @Autowired
    @Qualifier("authConfig")
    AuthConfig authConfig;
    @Autowired
    @Qualifier("decoder")
    Base64.Decoder decoder;

    Key hmacKey = null;

    @PostConstruct
    public void init() {
        hmacKey = new SecretKeySpec(decoder.decode(authConfig.getModel().getKey()),
                SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateAccessToken(int id, int expirationTimeSeconds) {
        var dateNow = Date.from(Instant.ofEpochMilli(System.currentTimeMillis()));
        String accessToken = Jwts.builder()
                .claim("id", id)
                .signWith(hmacKey)
                .setIssuedAt(dateNow)
                .setExpiration(Date.from(dateNow.toInstant().plusSeconds(expirationTimeSeconds)))
                .compact();

        return accessToken;
    }

    public String generateRefreshToken(int expirationTimeSeconds) {
        var dateNow = Date.from(Instant.ofEpochMilli(System.currentTimeMillis()));
        String accessToken = Jwts.builder()
                .signWith(hmacKey)
                .setIssuedAt(dateNow)
                .setExpiration(Date.from(dateNow.toInstant().plusSeconds(expirationTimeSeconds)))
                .compact();

        return accessToken;
    }

    public TokenData decodeToken(String jwt) throws ExpiredJwtException {
        var claims = Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(jwt).getBody();
        return new TokenData(claims.get("id", Integer.class), claims.getExpiration());
    }
}
