package com.passwordbox.exceptions;

public class ProfileLockStateException extends RuntimeException {
    public ProfileLockStateException(String message) {
        super(message);
    }
}
