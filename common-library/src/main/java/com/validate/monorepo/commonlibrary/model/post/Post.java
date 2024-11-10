package com.validate.monorepo.commonlibrary.model.post;

import org.springframework.data.annotation.Id;

public record Post(
		@Id String id,
		String title,
		String description,
		long upVoteCount,
		long downVoteCount,
		long replyCount,
		String author,
		PostTag tag,
		boolean isDeleted,
		long createdAt
) {
	
	public Post deletePost() {
		return new Post( id, title, description, upVoteCount, downVoteCount,
				replyCount, author, tag, true, createdAt);
	}
	
	public Post tagPost(PostTag tag) {
		return new Post( id, title, description, upVoteCount, downVoteCount,
				replyCount, author, tag, isDeleted, createdAt);
	}
	
}
