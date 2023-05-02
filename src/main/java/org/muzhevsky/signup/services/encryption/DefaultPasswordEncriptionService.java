package org.muzhevsky.signup.services.encryption;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
public class DefaultPasswordEncriptionService implements PasswordEncryptionService{
    @Override
    @SneakyThrows
    public byte[] encryptPassword(byte[] password, byte[] salt) {
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        md.update(salt);
        md.update(password);

        return md.digest();
    }
}
