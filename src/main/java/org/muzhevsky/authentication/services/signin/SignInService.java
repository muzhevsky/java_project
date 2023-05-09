package org.muzhevsky.authentication.services.signin;

import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.authentication.dtos.SignInForm;
import org.muzhevsky.authentication.exceptions.UserNotFoundException;
import org.muzhevsky.authentication.exceptions.WrongPasswordException;

public interface SignInService {
    TokenPair signIn(SignInForm signInForm) throws UserNotFoundException, WrongPasswordException;
}
