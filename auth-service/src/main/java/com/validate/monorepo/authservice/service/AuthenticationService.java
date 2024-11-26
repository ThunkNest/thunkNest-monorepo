package com.validate.monorepo.authservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.model.auth.OauthProvider;
import com.validate.monorepo.commonlibrary.model.auth.UserAuthRequest;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import com.validate.monorepo.commonlibrary.repository.mongo.UserRepository;
import com.validate.monorepo.commonlibrary.util.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

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
				Instant.now().toEpochMilli());
		
		return userRepository.save(newUser);
	}
	
	public User authenticate(UserAuthRequest request) {
		final String email = request.email();
		if (!EmailUtils.isValidEmail(email)) throw new BadRequestException("Provided email is not valid");
		
		Optional<User> findUser = userRepository.findByEmail(email);
		
		if (findUser.isPresent()) {
			return findUser.get();
		} else {
			log.info("User with email={} does not exist creating new user", email);
			return createUser(email, request.provider(), request.providerId());
		}
	}
	
	private String createUniqueUserName() {
		String userName = userNameGeneratorService.generateUsername();
		if (userRepository.findByUsername(userName).isPresent()) createUniqueUserName();
		
		return userName;
	}
}
