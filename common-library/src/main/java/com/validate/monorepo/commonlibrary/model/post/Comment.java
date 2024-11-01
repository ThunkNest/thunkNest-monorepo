package com.validate.monorepo.commonlibrary.model.post;

import org.springframework.data.annotation.Id;
import java.util.ArrayList;
import java.util.List;

public record Comment(
		@Id String id,
		String text,
		String author,
		long createdAt,
		long likeCount,
		long upVoteCount,
		long downVoteCount,
		String parentCommentId,
		List<Comment> replies
) {
	public Comment likeComment() {
		return new Comment(id, text, author, createdAt, likeCount + 1, upVoteCount, downVoteCount, parentCommentId, replies);
	}

	public Comment upVote() {
		return new Comment(id, text, author, createdAt, likeCount, upVoteCount + 1, downVoteCount, parentCommentId, replies);
	}

	public Comment downVote() {
		return new Comment(id, text, author, createdAt, likeCount, upVoteCount, downVoteCount + 1, parentCommentId, replies);
	}

	public Comment addReply(Comment comment) {
		List<Comment> updatedReplies = new ArrayList<>(replies);
		updatedReplies.add(comment);
		return new Comment(id, text, author, createdAt, likeCount, upVoteCount, downVoteCount, parentCommentId, updatedReplies);
	}

	public Comment addReplies(List<Comment> comments) {
		List<Comment> updatedReplies = new ArrayList<>(replies);
		updatedReplies.addAll(comments);
		return new Comment(id, text, author, createdAt, likeCount, upVoteCount, downVoteCount, parentCommentId, updatedReplies);
	}
}
