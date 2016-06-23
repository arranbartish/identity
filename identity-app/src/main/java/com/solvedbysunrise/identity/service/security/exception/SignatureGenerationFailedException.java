package com.solvedbysunrise.identity.service.security.exception;

public class SignatureGenerationFailedException extends RuntimeException {

    public SignatureGenerationFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
