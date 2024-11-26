package com.validate.monorepo.commonlibrary.model.user.mongo;

import com.validate.monorepo.commonlibrary.model.auth.OauthProvider;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public record User(
		@Id
		String  id,
		String username,
		OauthProvider oauthProvider,
		String providerId,
		long reputationScore,
		String email,
		String phoneNumber,
		long createdAt
) {
	
	public User upVoteReputationIncrease() {
		return new User(id, username, oauthProvider, providerId, reputationScore + 3, email, phoneNumber,
				createdAt);
	}
	
	public User upVoteReputationIncreaseFromAuthor() {
		return new User(id, username, oauthProvider, providerId, reputationScore + 5, email, phoneNumber,
				createdAt);
	}
	
	public User downVoteReputationDecrease() {
		return new User(id, username, oauthProvider, providerId, reputationScore - 1, email, phoneNumber,
				createdAt);
	}
	
}
