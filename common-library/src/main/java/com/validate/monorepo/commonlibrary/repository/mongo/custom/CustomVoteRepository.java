package com.validate.monorepo.commonlibrary.repository.mongo.custom;

import com.validate.monorepo.commonlibrary.model.vote.mongo.VoteType;

import java.util.List;

public interface CustomVoteRepository {
	
	void upsertVote(String userId, String postId, String replyId, VoteType voteType);
	void removeVote(String userId, String postId, String replyId);
	long countVotesByPost(String postId, VoteType voteType);
	long countVotesByReply(String replyId, VoteType voteType);
	boolean hasUserVoted(String userId, String postId, String replyId);
	List<String> findAllVotesByUser(String userId);
	List<String> findVotesByUserAndVoteType(String userId, VoteType voteType);
	
}
