package com.validate.monorepo.voteservice.service;

import com.validate.monorepo.commonlibrary.model.vote.mongo.VoteRequest;
import com.validate.monorepo.commonlibrary.repository.mongo.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
	
	private final VoteRepository voteRepository;
	
	public VoteService(VoteRepository voteRepository) {
		this.voteRepository = voteRepository;
	}
	
	public void addVote(VoteRequest request) {
		request.validate();
		
		voteRepository.upsertVote(request.voterUserId(), request.postId(), request.replyId(), request.action());
	}
	
	public void removeVote(VoteRequest request) {
		request.validate();
		
		voteRepository.removeVote(request.voterUserId(), request.postId(), request.replyId());
	}
	
}
