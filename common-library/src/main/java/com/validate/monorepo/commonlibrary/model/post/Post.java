package com.validate.monorepo.commonlibrary.model.post;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
public record Post(
		@Id String id,
		String title,
		String description,
		long likeCount,
		List<Comment> comments,
		String author,
		long createdAt
) {
	public Post addComment(Comment comment) {
		List<Comment> updatedComments = new ArrayList<>(comments);
		updatedComments.add(comment);
		return new Post(id, title, description, likeCount, updatedComments, author, createdAt);
	}
	
	public Post addComments(List<Comment> comments) {
		List<Comment> updatedComments = new ArrayList<>(comments);
		updatedComments.addAll(comments);
		return new Post(id, title, description, likeCount, updatedComments, author, createdAt);
	}
	
	public Post likePost() {
		return new Post(id, title, description, likeCount + 1, comments, author, createdAt);
	}
}
