package org.muzhevsky.signup.services.signin;

import org.muzhevsky.authorization.models.TokenPair;
import org.muzhevsky.signup.dtos.SignInForm;
import org.muzhevsky.signup.exceptions.UserNotFoundException;
import org.muzhevsky.signup.exceptions.WrongPasswordException;

public interface SignInService {
    TokenPair signIn(SignInForm signInForm) throws UserNotFoundException, WrongPasswordException;
}
