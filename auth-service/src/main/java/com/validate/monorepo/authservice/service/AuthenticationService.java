package com.validate.monorepo.authservice.service;

import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.auth.OauthProvider;
import com.validate.monorepo.commonlibrary.model.auth.UserAuthRequest;
import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.repository.neo4j.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AuthenticationService {
	
	private final UserRepository userRepository;
	private final UserNameGeneratorService userNameGeneratorService;
	
	public AuthenticationService(UserRepository userRepository, UserNameGeneratorService userNameGeneratorService) {
		this.userRepository = userRepository;
		this.userNameGeneratorService = userNameGeneratorService;
	}
	
	public User createUser(String emailAddress, OauthProvider provider, String googleId) {
		String userName = createUniqueUserName();
		
		User newUser = new User(null, userName, provider, googleId, 0, emailAddress, null,
				List.of(), List.of(), LocalDateTime.now());
		
		return userRepository.save(newUser);
	}
	
	public User getUserById(UUID id) {
		return userRepository.findById(id).orElseThrow(() ->
				new NotFoundException(String.format("User with Id=%s not found", id)));
	}
	
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() ->
				new NotFoundException(String.format("User with email=%s not found", email)));
	}
	
	public User authenticate(UserAuthRequest request) {
		if (accountExists(request.email())) {
			return getUserByEmail(request.email());
		} else {
			log.info("User with email={} does not exist creating new user", request.email());
			return createUser(request.email(), request.provider(), request.providerId());
		}
	}
	
	public boolean accountExists(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
	
	private String createUniqueUserName() {
		String userName = userNameGeneratorService.generateUsername();
		if (userRepository.findByUsername(userName).isPresent()) createUniqueUserName();
		
		return userName;
	}
}
