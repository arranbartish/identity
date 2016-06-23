package com.solvedbysunrise.identity.service.exception;

public class AccountCannotBeDeactivatedException extends RuntimeException {

    public AccountCannotBeDeactivatedException(String message) {
        super(message);
    }

}
