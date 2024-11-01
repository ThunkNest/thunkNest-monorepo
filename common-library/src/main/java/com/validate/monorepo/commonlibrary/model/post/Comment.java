package com.validate.monorepo.commonlibrary.model.post;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

public record Comment(
		@Id String id,
		String text,
		String author,
		long createdAt,
		long likeCount,
		String parentCommentId,
		List<Comment> replies
) {
	public Comment likeComment() {
		return new Comment(id, text, author, createdAt, likeCount + 1, parentCommentId, replies);
	}
	
	public Comment addReply(Comment comment) {
		List<Comment> updatedReplies = new ArrayList<>(replies);
		updatedReplies.add(comment);
		return new Comment(id, text, author, createdAt, likeCount, parentCommentId, updatedReplies);
	}
	
	public Comment addReplies(List<Comment> comments) {
		List<Comment> updatedReplies = new ArrayList<>(replies);
		updatedReplies.addAll(comments);
		return new Comment(id, text, author, createdAt, likeCount, parentCommentId, updatedReplies);
	}
}
