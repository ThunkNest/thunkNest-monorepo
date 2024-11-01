package com.validate.monorepo.commonlibrary.model.post;

import java.util.List;

public record Comment(
		String id,
		String text,
		String author,
		long createdAt,
		long likeCount,
		List<Comment> replies
) {
	
	public Comment likeComment() {
		return new Comment(id, text, author, createdAt, likeCount() + 1, replies);
	}
	
	public Comment addReply(Comment comment) {
		List<Comment> repliesList = replies();
		repliesList.add(comment);
		return new Comment(id, text, author, createdAt, likeCount, repliesList);
	}
	
	public Comment addReplies(List<Comment> comments) {
		List<Comment> repliesList = replies();
		repliesList.addAll(comments);
		return new Comment(id, text, author, createdAt, likeCount, repliesList);
	}
	
}
