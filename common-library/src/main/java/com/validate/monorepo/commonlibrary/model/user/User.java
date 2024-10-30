package com.validate.monorepo.commonlibrary.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Document(collection = "users")
public record User(
		@Id String id,
		String userName,
		String googleId,
		OauthProvider oauthProvider,
		String email,
		String phone,
		long valid8Score,
		long commentCount,
		Boolean emailNotificationEnabled,
		Date dateOfBirth,
		long createdAt,
		Long updatedAt
) {
	
	public User incrementCommentCount() {
		long newCommentCount = commentCount() + 1;
		return new User(id, userName, googleId, oauthProvider, email, phone, valid8Score, newCommentCount,
				emailNotificationEnabled, dateOfBirth, createdAt, Instant.now().toEpochMilli());
	}
	
	public User incrementValid8ScoreFromLike() {
		long newValid8Score = valid8Score() + 3;
		return new User(id, userName, googleId, oauthProvider, email, phone, newValid8Score, commentCount,
				emailNotificationEnabled, dateOfBirth, createdAt, Instant.now().toEpochMilli());
	}
	
}
