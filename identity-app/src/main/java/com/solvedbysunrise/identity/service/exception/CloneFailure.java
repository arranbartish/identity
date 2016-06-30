package com.solvedbysunrise.identity.service.exception;

public class CloneFailure extends RuntimeException {
    public CloneFailure(String message, Exception cause) {
        super(message, cause);
    }
}
