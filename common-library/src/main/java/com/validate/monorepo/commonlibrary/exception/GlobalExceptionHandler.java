package com.validate.monorepo.commonlibrary.exception;

import com.validate.monorepo.commonlibrary.response.GlobalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleException(RuntimeException ex) {
		logger.error("Runtime exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR
		);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleException(MethodArgumentNotValidException ex) {
		logger.error("Method argument not valid exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				ex.getMessage(),
				HttpStatus.BAD_REQUEST
		);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleException(UserNotFoundException ex) {
		logger.error("User not found exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				ex.getMessage(),
				HttpStatus.NOT_FOUND
		);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleBadRequestException(BadRequestException ex) {
		logger.error("Bad request exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				ex.getMessage(),
				HttpStatus.BAD_REQUEST
		);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleException(ForbiddenException ex) {
		logger.error("Forbidden exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				ex.getMessage(),
				HttpStatus.FORBIDDEN
		);
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleException(UnauthorizedException ex) {
		logger.error("Unauthorized exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				ex.getMessage(),
				HttpStatus.UNAUTHORIZED
		);
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleException(NotFoundException ex) {
		logger.error("Resource not found exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				ex.getMessage(),
				HttpStatus.NOT_FOUND
		);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FileNotAllowedException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleException(FileNotAllowedException ex) {
		logger.error("File not allowed exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				"Invalid file type. Only PNG and JPEG are accepted.",
				HttpStatus.BAD_REQUEST
		);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RabbitPublisherException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleException(RabbitPublisherException ex) {
		logger.error("Rabbit publisher exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR
		);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MatchSerializationException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleException(MatchSerializationException ex) {
		logger.error("Match serialization exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR
		);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HttpWebException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleException(HttpWebException ex) {
		logger.error("HTTP web call exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				ex.getMessage(),
				HttpStatus.SERVICE_UNAVAILABLE
		);
		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(IOException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleIOException(IOException ex) {
		logger.error("IO exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				"Failed to retrieve image",
				HttpStatus.INTERNAL_SERVER_ERROR
		);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidLikeException.class)
	public final ResponseEntity<GlobalResponse<Object>> handleIOException(InvalidLikeException ex) {
		logger.error("Invalid like exception: ", ex);
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				"Invalid like operation",
				HttpStatus.BAD_REQUEST
		);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GlobalResponse<Object>> handleSecurityException(Exception exception) {
		logger.error("AuthN/AuthZ exception: ", exception);
		
		GlobalResponse<Object> response = new GlobalResponse<>(
				false,
				null,
				"Unknown internal server error.",
				HttpStatus.INTERNAL_SERVER_ERROR
		);
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

