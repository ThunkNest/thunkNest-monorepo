package com.validate.monorepo.commonlibrary.model.post;

import com.validate.monorepo.commonlibrary.model.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Node
public record Post(
		@Id
		@GeneratedValue(generatorClass = GeneratedValue.UUIDGenerator.class)
		UUID id,
		String title,
		String description,
		boolean isDeleted,
		
		@Relationship(type = "CREATED", direction = Relationship.Direction.INCOMING)
		User author,
		
		@Relationship(type = "UPVOTED_BY", direction = Relationship.Direction.INCOMING)
		List<User> upvotedBy,
		
		@Relationship(type = "DOWNVOTED_BY", direction = Relationship.Direction.INCOMING)
		List<User> downvotedBy,
		
		@Relationship(type = "REPLIED_TO", direction = Relationship.Direction.OUTGOING)
		List<Reply> replies,
		@CreatedDate
		LocalDateTime createdAt
) { }
