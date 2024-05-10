package com.passwordbox.exceptions;

public class InvalidPassportDateException extends RuntimeException {

    public InvalidPassportDateException(String message) {
        super(message);
    }
}
