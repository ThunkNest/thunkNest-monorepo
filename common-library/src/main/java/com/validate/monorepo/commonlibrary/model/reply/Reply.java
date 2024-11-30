package com.validate.monorepo.commonlibrary.model.reply;

import com.validate.monorepo.commonlibrary.model.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "replies")
public record Reply(
		@Id
		String id,
		String text,
		int upVoteCount,
		int downVoteCount,
		User author,
		String parentPostId,
		
		List<User> taggedUsers,
		boolean isEdited,
		boolean isDeleted,
		long deletedAt,
		
		long createdAt
) {
	
	public Reply deleteReply() {
		return new Reply(id, text, upVoteCount, downVoteCount, author, parentPostId, taggedUsers, isEdited, true,
				Instant.now().toEpochMilli(), createdAt);
	}
	
}
