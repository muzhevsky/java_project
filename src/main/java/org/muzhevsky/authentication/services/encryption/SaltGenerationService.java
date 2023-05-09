package org.muzhevsky.authentication.services.encryption;

public interface SaltGenerationService {
    byte[] getSalt();
}
