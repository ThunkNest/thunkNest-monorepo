package com.validate.monorepo.commonlibrary.model.post;

import org.springframework.data.annotation.Id;

public record Reply(
		@Id String id,
		String postId,
		String parentReplyId,
		String text,
		String author,
		long upVoteCount,
		long downVoteCount,
		long replyCount,
		boolean isDeleted,
		long createdAt
) {
	
	public Reply deleteReply() {
		return new Reply(id, postId, parentReplyId, text, author, upVoteCount,
				downVoteCount, replyCount, true, createdAt);
	}
	
}
