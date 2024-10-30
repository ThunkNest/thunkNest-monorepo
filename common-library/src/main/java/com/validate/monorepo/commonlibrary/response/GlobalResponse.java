package com.validate.monorepo.commonlibrary.response;

import org.springframework.http.HttpStatus;

public record GlobalResponse<T>(
		boolean success,
		T data,
		String message,
		int statusCode
) {
	
	public GlobalResponse(boolean success, T data, String message, HttpStatus status) {
		this(success, data, message, status.value());
	}
	
	public boolean isSuccess() {
		return this.success();
	}
}
