package com.validate.monorepo.userservice.service;

import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.repository.neo4j.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getUserById(UUID id) {
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
		return userRepository.searchByUsername(fragment);
	}
	
	public void deleteUserById(UUID id) {
		userRepository.deleteUserById(id);
	}
	
}
