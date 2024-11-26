package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.post.mongo.Post;

import java.util.List;

public interface CustomPostRepository {
	
	void addReplyToPost(String postId, String replyId);
	void removeReplyFromPost(String postId, String replyId);
	List<Post> findAllPostsByAuthor(String userId);
	List<Post> findAllPostsUserInteractedWith(String userId);

}
