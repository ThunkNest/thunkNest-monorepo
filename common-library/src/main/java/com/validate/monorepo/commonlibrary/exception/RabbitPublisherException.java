package com.validate.monorepo.commonlibrary.exception;

public class RabbitPublisherException extends RuntimeException {
	public RabbitPublisherException() {
		super();
	}
	
	public RabbitPublisherException(String message) {
		super(message);
	}
	
	public RabbitPublisherException(String message, Throwable cause) {
		super(message, cause);
	}
}

