package com.validate.monorepo.commonlibrary.model.post.mongo;

import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "posts")
public record Post(
		@Id
		String id,
		String title,
		String description,
		boolean isDeleted,
		int upVoteCount,
		int downVoteCount,
		boolean openToCoFounder,
		User author,
		
		List<String> replies,
		
		long createdAt
) {
	
	public Post deletePost() {
		return new Post(id, title, description, true, upVoteCount, downVoteCount, openToCoFounder, author, replies, createdAt);
	}

}
