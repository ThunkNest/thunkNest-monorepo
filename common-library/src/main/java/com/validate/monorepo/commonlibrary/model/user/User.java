package com.validate.monorepo.commonlibrary.model.user;

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

}
