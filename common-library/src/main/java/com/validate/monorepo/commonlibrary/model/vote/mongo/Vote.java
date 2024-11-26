package com.validate.monorepo.commonlibrary.model.vote.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "votes")
public record Vote(
		@Id
		String id,
		String userId,
		String postId,
		String replyId,
		VoteType isUpvote,
		long createdAt
) {}