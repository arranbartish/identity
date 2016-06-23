package com.solvedbysunrise.identity.service.exception;

public class ActivationEmailCannotBeUsedException extends RuntimeException {

    public ActivationEmailCannotBeUsedException(String message) {
        super(message);
    }

}
