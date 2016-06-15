package com.solvedbysunrise.identity.data.dao.exception;

import com.solvedbysunrise.identity.problem.IdentityException;

public class ActivationEmailNotFoundException extends IdentityException {

    private static final long serialVersionUID = 3827938228388L;

    public ActivationEmailNotFoundException() {
        this(null, null);
    }

    public ActivationEmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActivationEmailNotFoundException(String message) {
        this(message, null);
    }

    public ActivationEmailNotFoundException(Throwable cause) {
        this(null, cause);
    }

}
