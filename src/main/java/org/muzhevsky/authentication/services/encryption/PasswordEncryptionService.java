package org.muzhevsky.authentication.services.encryption;

public interface PasswordEncryptionService {
    byte[] encryptPassword(byte[] password, byte[] salt);
}
