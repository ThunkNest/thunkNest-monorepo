package com.validate.monorepo.commonlibrary.model.post.neo4j;

import com.validate.monorepo.commonlibrary.model.reply.neo4j.Reply;
import com.validate.monorepo.commonlibrary.model.user.neo4j.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Post(
		@Id
		@GeneratedValue(generatorClass = GeneratedValue.UUIDGenerator.class)
		UUID id,
		String title,
		String description,
		boolean isDeleted,
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
		
		@CreatedDate
		LocalDateTime createdAt
) {
	
	public Post deletePost() {
		return new Post(id, title, description, true, upVoteCount, downVoteCount, author, upvotedBy, downvotedBy, replies, createdAt);
	}
}
