package com.solvedbysunrise.identity.data.dao.exception;

import com.solvedbysunrise.identity.problem.IdentityException;

public class DeactivationEmailNotFoundException extends IdentityException {

    private static final long serialVersionUID = 7729403528503L;

    public DeactivationEmailNotFoundException() {
        this(null, null);
    }

    public DeactivationEmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeactivationEmailNotFoundException(String message) {
        this(message, null);
    }

    public DeactivationEmailNotFoundException(Throwable cause) {
        this(null, cause);
    }

}
