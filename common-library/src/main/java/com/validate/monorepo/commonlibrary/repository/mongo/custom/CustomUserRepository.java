package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.user.User;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository {
	
	Optional<User> findByUsername(final String username);
	Optional<User> findByEmail(final String email);
	List<User> searchByUsername(final String usernameFragment);
	User upvoteReputationIncrease(String userId);
	User removeUpvoteReputationIncrease(String userId);
	User downVoteReputationDecrease(String userId);
	User removeDownVoteReputationDecrease(String userId);

}
