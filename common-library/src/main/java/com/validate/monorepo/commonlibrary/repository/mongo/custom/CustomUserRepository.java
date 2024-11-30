package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.user.User;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository {
	
	Optional<User> findByUsername(final String username);
	Optional<User> findByEmail(final String email);
	List<User> searchByUsername(final String usernameFragment);
	void upvoteReputationIncrease(String userId);
	void removeUpvoteReputationIncrease(String userId);
	void downVoteReputationDecrease(String userId);
	void removeDownVoteReputationDecrease(String userId);

}
