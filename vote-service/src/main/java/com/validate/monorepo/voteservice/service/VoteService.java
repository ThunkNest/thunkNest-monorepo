package com.validate.monorepo.voteservice.service;

import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.ampq.EventMessage;
import com.validate.monorepo.commonlibrary.model.vote.Vote;
import com.validate.monorepo.commonlibrary.model.vote.VoteRequest;
import com.validate.monorepo.commonlibrary.model.vote.VoteType;
import com.validate.monorepo.commonlibrary.rabbitmq.EventPublisher;
import com.validate.monorepo.commonlibrary.repository.mongo.PostRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.ReplyRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.UserRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.VoteRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class VoteService {
	
	private final VoteRepository voteRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	private final EventPublisher eventPublisher;
	
	public VoteService(VoteRepository voteRepository, UserRepository userRepository, PostRepository postRepository,
	                   ReplyRepository replyRepository, EventPublisher eventPublisher) {
		this.voteRepository = voteRepository;
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.replyRepository = replyRepository;
		this.eventPublisher = eventPublisher;
	}
	
	public void addVote(VoteRequest request) {
		request.validate();
		verifyValuesExist(request);
		
		voteRepository.upsertVote(request.voterUserId(), request.postId(), request.replyId(), request.action());
		eventPublisher.publishVoteEvent(generateEvent(request));
	}
	
	public void removeVote(VoteRequest request) {
		request.validate();
		verifyValuesExist(request);
		
		Vote removedVote = voteRepository.removeVote(request.voterUserId(), request.postId(), request.replyId());
		VoteRequest removeVoteRequest = new VoteRequest(removedVote.voteType(), request.postId(), request.replyId(), request.voterUserId());
		eventPublisher.publishVoteEvent(generateEvent(removeVoteRequest));
	}
	
	private EventMessage<VoteRequest> generateEvent(VoteRequest request) {
		return new EventMessage<>(UUID.randomUUID().toString(), request, Instant.now().toEpochMilli());
	}
	
	private void verifyValuesExist(VoteRequest request) {
		userRepository.findById(request.voterUserId())
				.orElseThrow(() -> new NotFoundException("User does not exist"));
		
		if (request.postId() != null) {
			postRepository.findById(request.postId())
					.orElseThrow(() -> new NotFoundException("Post does not exist"));
		} else {
			replyRepository.findById(request.replyId())
					.orElseThrow(() -> new NotFoundException("Reply does not exist"));
		}
	}
	
}
