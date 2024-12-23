package com.validate.monorepo.userservice.controller;

import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.util.BlankUtils;
import com.validate.monorepo.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@Operation(
			summary = "Get a user by email",
			description = "Returns specified user details if they exist")
	@GetMapping("/get-by-email/{email}")
	@ResponseStatus(value = HttpStatus.OK)
	public User getUserByEmail(@PathVariable String email) {
		BlankUtils.validateBlank(email);
		return userService.getUserByEmail(email);
	}
	
	@Operation(
			summary = "Get a user by their Id",
			description = "Returns specified user details if they exist")
	@GetMapping("/get-by-id/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public User getUserById(@PathVariable String id) {
		BlankUtils.validateBlank(id);
		return userService.getUserById(id);
	}
	
	@Operation(
			summary = "Get a user by their username",
			description = "Returns specified user details if they exist")
	@GetMapping("/get-by-username/{username}")
	@ResponseStatus(value = HttpStatus.OK)
	public User getUserByUsername(@PathVariable String username) {
		BlankUtils.validateBlank(username);
		return userService.getUserByUsername(username);
	}

	// Todo: Delete get all users endpoint
	@GetMapping("/get-all")
	@ResponseStatus(value = HttpStatus.OK)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@Operation(
			summary = "search for user by username",
			description = "Returns users that have usernames matching the provided fragment")
	@GetMapping("/search/{fragment}")
	@ResponseStatus(value = HttpStatus.OK)
	public List<User> searchForUsers(@PathVariable String fragment) {
		BlankUtils.validateBlank(fragment);
		return userService.searchUsers(fragment);
	}
	
	@Operation(
			summary = "Delete a user by their Id",
			description = "Deletes a user and all relationships to other nodes id they exist")
	@DeleteMapping("/delete-by-id/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteUserById(@PathVariable String id) {
		BlankUtils.validateBlank(id);
		userService.deleteUserById(id);
	}
	
}
