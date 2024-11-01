package com.validate.monorepo.authservice.service;

import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.auth.UserAuthRequest;
import com.validate.monorepo.commonlibrary.model.user.OauthProvider;
import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationService {
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
	private final UserRepository userRepository;
	private final UserNameGeneratorService userNameGeneratorService;
	
	public AuthenticationService(UserRepository userRepository, UserNameGeneratorService userNameGeneratorService) {
		this.userRepository = userRepository;
		this.userNameGeneratorService = userNameGeneratorService;
	}
	
	public User createUser(String emailAddress, OauthProvider provider, String googleId) {
		String userName = createUniqueUserName();
		
		User newUser = new User(null, userName, googleId, OauthProvider.GOOGLE, emailAddress, null,
				0,0, true, null, Instant.now().toEpochMilli(),
				null);
		
		return userRepository.save(newUser);
	}
	
	public User getUserById(String id) {
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
		if (userRepository.findByUserName(userName).isPresent()) createUniqueUserName();
		
		return userName;
	}
}
