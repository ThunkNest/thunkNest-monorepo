package com.validate.monorepo.userservice.service;

import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import com.validate.monorepo.commonlibrary.repository.mongo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getUserById(String id) {
		return userRepository.findById(id).orElseThrow(() ->
				new NotFoundException(String.format("User with Id=%s not found", id)));
	}
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() ->
				new NotFoundException(String.format("User with email=%s not found", email)));
	}
	
	public User getUserByUsername(String userName) {
		return userRepository.findByUsername(userName).orElseThrow(() ->
				new NotFoundException(String.format("User with username=%s not found", userName)));
	}
	
	// Todo: Delete get all users method
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public List<User> searchUsers(String fragment) {
		if (Objects.equals(fragment, "-")) throw new IllegalArgumentException("Disallowed character search");
		return userRepository.searchByUsername(fragment);
	}
	
	public void deleteUserById(String id) {
		userRepository.deleteById(id);
	}
	
}
