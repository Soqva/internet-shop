package com.s0qva.application.exception;

public class InvalidPasswordDuringSignInException extends RuntimeException {

    public InvalidPasswordDuringSignInException(String message) {
        super(message);
    }
}
