package com.solvedbysunrise.identity.data.dao.exception;

import com.solvedbysunrise.identity.problem.IdentityException;

public class EmailNotFoundException extends IdentityException {

    private static final long serialVersionUID = 32422332586435L;

    public EmailNotFoundException() {
        this(null, null);
    }

    public EmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotFoundException(String message) {
        this(message, null);
    }

    public EmailNotFoundException(Throwable cause) {
        this(null, cause);
    }

}
