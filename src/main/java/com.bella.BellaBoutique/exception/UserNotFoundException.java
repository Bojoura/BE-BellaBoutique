package com.bella.BellaBoutique.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String username) {
        super("Cannot find user " + username);
    }


}
