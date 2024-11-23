package com.validate.monorepo.authservice.controller;

import com.validate.monorepo.authservice.service.AuthenticationService;
import com.validate.monorepo.commonlibrary.model.auth.UserAuthRequest;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Operation(
			summary = "Authenticate a user",
			description = "Returns specified user details if they exist, else create a new user")
	@PostMapping("/authenticate")
	@ResponseStatus(value = HttpStatus.OK)
	public User authenticateUser(@RequestBody UserAuthRequest request) {
		return authenticationService.authenticate(request);
	}
	
}
