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
		long upVoteCount,
		long downVoteCount,
		List<Comment> comments,
		String author,
		long createdAt
) {
	public Post addComment(Comment comment) {
		List<Comment> updatedComments = new ArrayList<>(comments);
		updatedComments.add(comment);
		return new Post(id, title, description, likeCount, upVoteCount, downVoteCount, updatedComments, author, createdAt);
	}

	public Post addComments(List<Comment> comments) {
		List<Comment> updatedComments = new ArrayList<>(comments);
		updatedComments.addAll(comments);
		return new Post(id, title, description, likeCount, upVoteCount, downVoteCount, updatedComments, author, createdAt);
	}

	public Post likePost() {
		return new Post(id, title, description, likeCount + 1, upVoteCount, downVoteCount, comments, author, createdAt);
	}

	public Post upVote() {
		return new Post(id, title, description, likeCount, upVoteCount + 1, downVoteCount, comments, author, createdAt);
	}

	public Post downVote() {
		return new Post(id, title, description, likeCount, upVoteCount, downVoteCount + 1, comments, author, createdAt);
	}
}
