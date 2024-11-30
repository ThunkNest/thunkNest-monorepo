package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.post.Post;

import java.util.List;

public interface CustomPostRepository {
	
	void addReplyToPost(String postId, String replyId);
	void removeReplyFromPost(String postId, String replyId);
	void updateVoteCount(String postId, long upVoteCount, long downVoteCount);
	List<Post> findAllPostsAndIsDeletedFalse();
	List<Post> findAllPostsByAuthor(String userId);
	List<Post> findAllPostsUserInteractedWith(String userId);
	
}
