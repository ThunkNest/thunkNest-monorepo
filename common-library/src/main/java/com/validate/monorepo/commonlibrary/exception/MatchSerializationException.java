package com.validate.monorepo.commonlibrary.exception;

public class MatchSerializationException extends RuntimeException {
	public MatchSerializationException() {
		super();
	}
	
	public MatchSerializationException(String message) {
		super(message);
	}
	
	public MatchSerializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
