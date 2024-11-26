package com.validate.monorepo.voteservice.service;

import com.validate.monorepo.commonlibrary.model.vote.mongo.VoteType;
import com.validate.monorepo.commonlibrary.repository.mongo.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteUtilityService {
	
	private final VoteRepository voteRepository;
	
	public VoteUtilityService(VoteRepository voteRepository) {
		this.voteRepository = voteRepository;
	}
	
	public long countVotesByPost(String postId, VoteType voteType) {
		return voteRepository.countVotesByPost(postId, voteType);
	}
	
	public long countVotesByReply(String replyId, VoteType voteType) {
		return voteRepository.countVotesByReply(replyId, voteType);
	}
	
	public boolean hasUserVoted(String userId, String postId, String replyId) {
		return voteRepository.hasUserVoted(userId, postId, replyId);
	}
	
	public List<String> findAllVotesByUser(String userId) {
		return voteRepository.findAllVotesByUser(userId);
	}
	
	public List<String> findVotesByUserAndVoteType(String userId, VoteType voteType) {
		return voteRepository.findVotesByUserAndVoteType(userId, voteType);
	}
	
}
