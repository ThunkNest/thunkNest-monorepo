package com.validate.monorepo.commonlibrary.exception;

public class InvalidLikeException extends RuntimeException {
	public InvalidLikeException() {
		super();
	}
	
	public InvalidLikeException(String message) {
		super(message);
	}
	
	public InvalidLikeException(String message, Throwable cause) {
		super(message, cause);
	}
}