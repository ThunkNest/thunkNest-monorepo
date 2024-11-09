package com.validate.monorepo.commonlibrary.model.neo;

import com.validate.monorepo.commonlibrary.model.user.OauthProvider;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDateTime;
import java.util.UUID;

@Node
public record User(
		@Id
		@GeneratedValue(GeneratedValue.UUIDGenerator.class)
		UUID id,
		String username,
		OauthProvider oauthProvider,
		String providerId,
		long reputationScore,
		String email,
		String phoneNumber,
				
		@CreatedDate
		LocalDateTime createdAt
) {}
