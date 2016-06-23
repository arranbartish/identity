package com.solvedbysunrise.identity.service.exception;

public class AuthenticationFailedException extends RuntimeException {

	private static final long serialVersionUID = -416501976475437430L;

	private final String requestSource;
	
	public AuthenticationFailedException(final String requestSource) {
		this(null, null, requestSource);
	}

	public AuthenticationFailedException(final String message, final Throwable cause, final String requestSource) {
		super(message, cause);
		this.requestSource = requestSource;
	}

	public AuthenticationFailedException(final String message, final String requestSource) {
		this(message, null, requestSource);
	}

	public AuthenticationFailedException(final Throwable cause, final String requestSource) {
		this(null, cause, requestSource);
	}

	public String getRequestSource() {
		return requestSource;
	}
}
