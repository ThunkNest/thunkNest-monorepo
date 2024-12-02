package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {
	
	void addReplyToPost(String postId, String replyId);
	void removeReplyFromPost(String postId, String replyId);
	void updateVoteCount(String postId, long upVoteCount, long downVoteCount);
	Page<Post> findAllPostsAndIsDeletedFalse(Pageable pageable);
	Page<Post> findAllPostsByAuthor(String userId, Pageable pageable);
	Page<Post> findAllPostsUserInteractedWith(String userId, Pageable pageable);
	void updateUserInAuthoredPosts(User user);
	
}
