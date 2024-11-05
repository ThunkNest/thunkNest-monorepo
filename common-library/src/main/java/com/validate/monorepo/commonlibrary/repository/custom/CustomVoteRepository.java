package com.validate.monorepo.commonlibrary.repository.custom;

import com.validate.monorepo.commonlibrary.model.vote.Votes;

public interface CustomVoteRepository {
	
	void upvotePost(String postId, String userId);
	void downVotePost(String postId, String userId);
	Votes getVotesForPost(String postId);
	long getUpvoteCountForPost(String postId);
	long getDownVoteCountForPost(String postId);
	
}
