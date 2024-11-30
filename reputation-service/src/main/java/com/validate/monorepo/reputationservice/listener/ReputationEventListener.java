package com.validate.monorepo.reputationservice.listener;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.model.ampq.EventMessage;
import com.validate.monorepo.commonlibrary.model.event.VoteEvent;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.reply.Reply;
import com.validate.monorepo.commonlibrary.model.vote.VoteRequest;
import com.validate.monorepo.commonlibrary.repository.mongo.PostRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.ReplyRepository;
import com.validate.monorepo.reputationservice.service.ReputationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReputationEventListener {
	
	private final ReputationService reputationService;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	
	public ReputationEventListener(ReputationService reputationService, PostRepository postRepository, ReplyRepository replyRepository) {
		this.reputationService = reputationService;
		this.postRepository = postRepository;
		this.replyRepository = replyRepository;
	}
	
	@RabbitListener(queues = "q.reputation.upVotes")
	public void handleUpVote(EventMessage<VoteRequest> eventMessage) {
		log.info("UserEventListener: received UPVOTE: {}", eventMessage);
		
		VoteRequest payload = eventMessage.payload();
		String authorId = getResourceAuthor(payload);
		String postId = payload.postId();
		String replyId = payload.replyId();
		if (payload.isRemoval()) reputationService.removeUpvoteReputationIncrease(authorId, postId, replyId);
		else reputationService.upvoteReputationIncrease(authorId, postId, replyId);
	}
	
	@RabbitListener(queues = "q.reputation.downVotes")
	public void handleDownVote(EventMessage<VoteRequest> eventMessage) {
		log.info("UserEventListener: received DOWNVOTE: {}", eventMessage);
		
		final VoteRequest payload = eventMessage.payload();
		String authorId = getResourceAuthor(payload);
		String postId = payload.postId();
		String replyId = payload.replyId();
		if (payload.isRemoval()) reputationService.removeDownVoteReputationDecrease(authorId, postId, replyId);
		else reputationService.downVoteReputationDecrease(authorId, postId, replyId);
	}
	
	String getResourceAuthor(VoteRequest payload) {
		if (payload.postId() != null) {
			Post post = postRepository.findById(payload.postId()).orElseThrow(() ->
					new BadRequestException(String.format("Post with id=%s does not exist", payload.postId())));
			return post.author().id();
		} else {
			Reply reply = replyRepository.findById(payload.replyId()).orElseThrow(() ->
					new BadRequestException(String.format("Post with id=%s does not exist", payload.replyId())));
			return reply.author().id();
		}
	}
	
}
