package com.validate.monorepo.voteservice.service;

import com.validate.monorepo.commonlibrary.model.vote.Votes;
import com.validate.monorepo.commonlibrary.repository.custom.CustomVoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
	
	private final CustomVoteRepository customVoteRepository;
	
	public VoteService(CustomVoteRepository customVoteRepository) {
		this.customVoteRepository = customVoteRepository;
	}
	
	public void upvotePost(String postId, String userId) {
		customVoteRepository.upvotePost(postId, userId);
	}
	
	public void downVotePost(String postId, String userId) {
		customVoteRepository.downVotePost(postId, userId);
	}
	
	public Votes getVotesForPost(String postId) {
		return customVoteRepository.getVotesForPost(postId);
	}
	
	public long getUpvoteCountForPost(String postId) {
		return customVoteRepository.getUpvoteCountForPost(postId);
	}
	
	public long getDownVoteCountForPost(String postId) {
		return customVoteRepository.getDownVoteCountForPost(postId);
	}
}
