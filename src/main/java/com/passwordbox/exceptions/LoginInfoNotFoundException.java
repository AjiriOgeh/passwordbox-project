package com.passwordbox.exceptions;

public class LoginInfoNotFoundException extends RuntimeException {
    public LoginInfoNotFoundException(String message) {
        super(message);
    }
}
