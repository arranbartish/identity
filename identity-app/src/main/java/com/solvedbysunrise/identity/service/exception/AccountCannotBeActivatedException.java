package com.solvedbysunrise.identity.service.exception;

public class AccountCannotBeActivatedException extends RuntimeException {

    public AccountCannotBeActivatedException(String message) {
        super(message);
    }
}
