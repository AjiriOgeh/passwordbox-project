package com.passwordbox.exceptions;

public class PassportNotFoundException extends RuntimeException {
    public PassportNotFoundException(String message) {
        super(message);
    }
}
