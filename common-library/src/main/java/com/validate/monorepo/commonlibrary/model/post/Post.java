package com.validate.monorepo.commonlibrary.model.post;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "posts")
public record Post(
		@Id String id,
		String title,
		String description,
		long likeCount,
		List<Comment> comments,
		String createdBy,
		long createdAt
) {
	
	public Post likePost() {
		return new Post(id, title, description, likeCount() + 1, comments, createdBy, createdAt);
	}
	
	public Post addReply(Comment comment) {
		List<Comment> commentList = comments();
		commentList.add(comment);
		return new Post(id, title, description, likeCount(), comments, createdBy, createdAt);
	}
	
	public Post addReplies(List<Comment> comments) {
		List<Comment> commentList = comments();
		commentList.addAll(comments);
		return new Post(id, title, description, likeCount(), comments, createdBy, createdAt);
	}
	
}
