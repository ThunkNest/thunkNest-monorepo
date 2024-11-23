package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.user.mongo.User;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository {
	
	Optional<User> findByUsername(final String username);
	Optional<User> findByEmail(final String email);
	List<User> searchByUsername(final String usernameFragment);

}
