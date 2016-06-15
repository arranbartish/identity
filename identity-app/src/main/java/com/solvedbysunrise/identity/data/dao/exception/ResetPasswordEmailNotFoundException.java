package com.solvedbysunrise.identity.data.dao.exception;

import com.solvedbysunrise.identity.problem.IdentityException;

public class ResetPasswordEmailNotFoundException extends IdentityException {

    private static final long serialVersionUID = 93294574385743L;

    public ResetPasswordEmailNotFoundException() {
        this(null, null);
    }

    public ResetPasswordEmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResetPasswordEmailNotFoundException(String message) {
        this(message, null);
    }

    public ResetPasswordEmailNotFoundException(Throwable cause) {
        this(null, cause);
    }

}
