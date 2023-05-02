package org.muzhevsky.authorization.exceptions;

public class TokenNotFoundException extends Exception {
    public TokenNotFoundException(){
        super("no such token in database");
    }
}
