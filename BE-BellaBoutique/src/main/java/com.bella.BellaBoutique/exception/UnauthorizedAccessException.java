package com.bella.BellaBoutique.exception;

public class UnauthorizedAccessException extends RuntimeException{

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException() {
        super();
    }
}
