package com.validate.monorepo.commonlibrary.model.vote.mongo;

import com.validate.monorepo.commonlibrary.model.user.neo4j.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "downVotes")
public record DownVote(
		@Id
		String id,
		User user,
		long createdAt
) {}
