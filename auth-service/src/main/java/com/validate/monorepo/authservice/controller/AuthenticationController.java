package com.validate.monorepo.authservice.controller;

import com.validate.monorepo.authservice.service.AuthenticationService;
import com.validate.monorepo.commonlibrary.model.auth.UserAuthRequest;
import com.validate.monorepo.commonlibrary.model.user.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
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
	
	@Operation(
			summary = "Get a user by email",
			description = "Returns specified user details if they exist")
	@GetMapping("/get-by-email/{email}")
	@ResponseStatus(value = HttpStatus.OK)
	public User getUserByEmail(@PathVariable String email) {
		return authenticationService.getUserByEmail(email);
	}
	
	@Operation(
			summary = "Get a user by their Id",
			description = "Returns specified user details if they exist")
	@PostMapping("/get-by-id/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public User getUserById(@PathVariable String id) {
		return authenticationService.getUserById(id);
	}
	
}
