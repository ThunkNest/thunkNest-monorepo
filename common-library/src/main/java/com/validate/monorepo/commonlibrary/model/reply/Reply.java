package com.validate.monorepo.commonlibrary.model.reply;

import com.validate.monorepo.commonlibrary.model.post.Post;
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
public record Reply(
		@Id
		@GeneratedValue(generatorClass = GeneratedValue.UUIDGenerator.class)
		UUID id,
		String text,
		int upVoteCount,
		int downVoteCount,
		
		@Relationship(type = "CREATED", direction = Relationship.Direction.INCOMING)
		User author,
		
		@Relationship(type = "UPVOTED_BY", direction = Relationship.Direction.INCOMING)
		List<User> upvotedBy,
		
		@Relationship(type = "DOWNVOTED_BY", direction = Relationship.Direction.INCOMING)
		List<User> downvotedBy,
		
		@Relationship(type = "HAS_REPLY", direction = Relationship.Direction.OUTGOING)
		List<Reply> replies,
		
		@Relationship(type = "BELONGS_TO", direction = Relationship.Direction.INCOMING)
		Post parentPost,
		
		@CreatedDate
		LocalDateTime createdAt
) {}
