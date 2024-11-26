package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.post.mongo.Post;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;

import java.util.List;

public interface CustomPostRepository {
	
	List<User> findPostUpvotedUsers(final String postId);
	List<User> findPostDownvotedUsers(final String postId );
	List<String> findPostReplies(final String postId);
	void addReplyToPost(String postId, String replyId);
	void removeReplyFromPost(String postId, String replyId);
	List<Post> findAllPostsByAuthor(String userId);
	List<Post> findAllPostsUserInteractedWith(String userId);

	/* Voting Repository Methods for Posts*/
//	void upVotePost(String postId, String userId);
//	void removeUpVote(String postId, String userId);
//	void downVotePost(String postId, String userId);
//	void removeDownVote(String postId, String userId);
}
