package com.validate.monorepo.commonlibrary.exception;

public class FileNotAllowedException extends RuntimeException {
	public FileNotAllowedException() {
		super();
	}
	
	public FileNotAllowedException(String message) {
		super(message);
	}
	
	public FileNotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}
}
