package org.muzhevsky.authentication.services.encryption;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SaltGenerationService16 implements SaltGenerationService{
    SecureRandom random = new SecureRandom();

    @Override
    public byte[] getSalt() {
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }
}
