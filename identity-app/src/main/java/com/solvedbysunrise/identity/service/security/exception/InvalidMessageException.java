package com.solvedbysunrise.identity.service.security.exception;

public class InvalidMessageException extends RuntimeException {

    public InvalidMessageException() {
        super("The message was invalid");
    }
}
