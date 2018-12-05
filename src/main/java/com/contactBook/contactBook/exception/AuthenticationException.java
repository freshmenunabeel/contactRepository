package com.contactBook.contactBook.exception;

public class AuthenticationException extends RuntimeException {
	private String exception;

	public AuthenticationException(String exception) {
		super(exception);
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "InputValidationFailure { " +
				"messages=" + exception +
				'}';

	}
}