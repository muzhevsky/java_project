package org.muzhevsky.signup.services.encryption;

public interface PasswordEncryptionService {
    byte[] encryptPassword(byte[] password, byte[] salt);
}
