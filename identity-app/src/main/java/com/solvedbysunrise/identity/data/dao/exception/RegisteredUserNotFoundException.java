package com.solvedbysunrise.identity.data.dao.exception;

import com.solvedbysunrise.identity.problem.IdentityException;

public class RegisteredUserNotFoundException extends IdentityException {

    private static final long serialVersionUID = 2192242086762868120L;

    public RegisteredUserNotFoundException() {
        this(null, null);
    }

    public RegisteredUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisteredUserNotFoundException(String message) {
        this(message, null);
    }

    public RegisteredUserNotFoundException(Throwable cause) {
        this(null, cause);
    }

}
