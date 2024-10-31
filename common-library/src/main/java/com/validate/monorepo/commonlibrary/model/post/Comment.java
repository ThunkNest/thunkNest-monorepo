package com.validate.monorepo.commonlibrary.model.post;

import java.util.List;

public record Comment(
		String id,
		String text,
		String author,
		long createdAt,
		long likeCount,
		List<Comment> comments
) {
	
	public Comment likeComment() {
		return new Comment(id, text, author, createdAt, likeCount() + 1, comments);
	}
	
	public Comment addReply(Comment comment) {
		List<Comment> commentList = comments();
		commentList.add(comment);
		return new Comment(id, text, author, createdAt, likeCount, commentList);
	}
	
	public Comment addReplies(List<Comment> comments) {
		List<Comment> commentList = comments();
		commentList.addAll(comments);
		return new Comment(id, text, author, createdAt, likeCount, commentList);
	}
	
}
