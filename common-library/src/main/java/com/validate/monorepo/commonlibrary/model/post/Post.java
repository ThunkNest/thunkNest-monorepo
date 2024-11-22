package com.validate.monorepo.commonlibrary.model.post;

import com.validate.monorepo.commonlibrary.model.reply.Reply;
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
	
	public Post {
		if (upvotedBy == null) {
			upvotedBy = List.of();
		}
		if (downvotedBy == null) {
			downvotedBy = List.of();
		}
		if (replies == null) {
			replies = List.of();
		}
	}
	
	public Post deletePost() {
		return new Post(id, title, description, true, upVoteCount, downVoteCount, author, upvotedBy, downvotedBy, replies, createdAt);
	}
}
