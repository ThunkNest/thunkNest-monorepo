package com.validate.monorepo.commonlibrary.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Set;

@ControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

	private final ObjectMapper objectMapper;

	public ApiResponseAdvice(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	private static final Set<String> EXCLUDED_PATHS = Set.of(
			"/v3/api-docs",
			"/swagger-ui.html",
			"/swagger-ui/index.html",
			"/swagger-ui"
	);
	
	private static final Set<MediaType> IMAGE_MEDIA_TYPES = Set.of(
			MediaType.IMAGE_JPEG,
			MediaType.IMAGE_PNG
	);
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}
	
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
	                              Class<? extends HttpMessageConverter<?>> selectedConverterType,
	                              ServerHttpRequest request, ServerHttpResponse response) {
		String path = request.getURI().getPath();
		if (isExcludedPath(path) || isImageResponse(selectedContentType) || body instanceof GlobalResponse<?>) {
			return body;
		}

		HttpStatus status = getHttpStatus(response);
		
		return new GlobalResponse<>(true, body, "Request was successful", status);
	}
	
	private boolean isExcludedPath(String path) {
		return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
	}
	
	private boolean isImageResponse(MediaType selectedContentType) {
		return IMAGE_MEDIA_TYPES.contains(selectedContentType);
	}
	
	private HttpStatus getHttpStatus(ServerHttpResponse response) {
		if (response instanceof org.springframework.http.server.ServletServerHttpResponse) {
			return HttpStatus.valueOf(((org.springframework.http.server.ServletServerHttpResponse) response).getServletResponse().getStatus());
			}
		return HttpStatus.OK;
	}
}


