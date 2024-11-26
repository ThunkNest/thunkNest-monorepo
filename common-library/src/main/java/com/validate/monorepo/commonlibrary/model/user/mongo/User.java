package com.validate.monorepo.commonlibrary.model.user.mongo;

import com.validate.monorepo.commonlibrary.model.auth.OauthProvider;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
		List<String> upVotes,
		List<String> downVotes,
		long createdAt
) {
	
	public User upVoteReputationIncrease() {
		return new User(id, username, oauthProvider, providerId, reputationScore + 3, email, phoneNumber,
				upVotes, downVotes, createdAt);
	}
	
	public User upVoteReputationIncreaseFromAuthor() {
		return new User(id, username, oauthProvider, providerId, reputationScore + 5, email, phoneNumber,
				upVotes, downVotes, createdAt);
	}
	
	public User downVoteReputationDecrease() {
		return new User(id, username, oauthProvider, providerId, reputationScore - 1, email, phoneNumber,
				upVotes, downVotes, createdAt);
	}
	
}
