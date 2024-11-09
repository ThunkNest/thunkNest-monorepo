package com.validate.monorepo.authservice.controller;

import com.validate.monorepo.authservice.service.NeoAuthService;
import com.validate.monorepo.commonlibrary.model.auth.UserAuthRequest;
import com.validate.monorepo.commonlibrary.model.neo.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/auth")
public class Neo4JAuthController {
	
	private final NeoAuthService authenticationService;

	public Neo4JAuthController(NeoAuthService authenticationService) {
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
	public User getUserById(@PathVariable UUID id) {
		return authenticationService.getUserById(id);
	}
	
}
