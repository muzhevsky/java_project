package org.muzhevsky.authentication.services.encryption;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Arrays;

@Service
public class DefaultPasswordEncriptionService implements PasswordEncryptionService{
    @Override
    @SneakyThrows
    public byte[] encryptPassword(byte[] password, byte[] salt) {
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        md.update(salt);
        md.update(password);

        System.out.println(Arrays.toString(salt));
        System.out.println(Arrays.toString(password));
        System.out.println(Arrays.toString(md.digest()));

        return md.digest();
    }
}
