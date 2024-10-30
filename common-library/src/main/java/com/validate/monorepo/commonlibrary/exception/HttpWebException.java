package com.validate.monorepo.commonlibrary.exception;

public class HttpWebException extends RuntimeException {
	public HttpWebException() {
		super();
	}
	
	public HttpWebException(String message) {
		super(message);
	}
	
	public HttpWebException(String message, Throwable cause) {
		super(message, cause);
	}
}
