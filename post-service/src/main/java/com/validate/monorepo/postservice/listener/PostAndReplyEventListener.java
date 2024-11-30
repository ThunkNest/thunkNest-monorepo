package com.validate.monorepo.postservice.listener;

import com.validate.monorepo.commonlibrary.model.ampq.EventMessage;
import com.validate.monorepo.commonlibrary.model.vote.VoteRequest;
import com.validate.monorepo.postservice.service.PostService;
import com.validate.monorepo.postservice.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostAndReplyEventListener {
	
	private final PostService postService;
	private final ReplyService replyService;
	
	public PostAndReplyEventListener(PostService postService, ReplyService replyService) {
		this.postService = postService;
		this.replyService = replyService;
	}
	
	@RabbitListener(queues = "q.postAndReply.upVotes")
	public void handleUpVote(EventMessage<VoteRequest> eventMessage) {
		log.info("PostAndReplyEventListener: received UPVOTE: {}", eventMessage);
		doVoting(eventMessage);
	}
	
	@RabbitListener(queues = "q.postAndReply.downVotes")
	public void handleDownVote(EventMessage<VoteRequest> eventMessage) {
		log.info("PostAndReplyEventListener: received DOWNVOTE: {}", eventMessage);
		doVoting(eventMessage);
	}
	
	private void doVoting(EventMessage<VoteRequest> eventMessage) {
		VoteRequest payload = eventMessage.payload();
		if (payload.postId() != null) postService.handleVote(payload.postId());
		else replyService.handleVote(payload.replyId());
	}
	
}
