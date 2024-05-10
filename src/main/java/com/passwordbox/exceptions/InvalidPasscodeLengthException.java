package com.passwordbox.exceptions;

public class InvalidPasscodeLengthException extends RuntimeException{
    public InvalidPasscodeLengthException(String message) {
        super(message);
    }
}
