package com.validate.monorepo.commonlibrary.model.user;

import com.validate.monorepo.commonlibrary.model.vote.DownVote;
import com.validate.monorepo.commonlibrary.model.vote.Upvote;
import com.validate.monorepo.commonlibrary.model.user.OauthProvider;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;
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
		
		@Relationship(type = "UPVOTED", direction = Relationship.Direction.OUTGOING)
		List<Upvote> upVotes,
		
		@Relationship(type = "DOWNVOTED", direction = Relationship.Direction.OUTGOING)
		List<DownVote> downVotes,
				
		@CreatedDate
		LocalDateTime createdAt
) {}
