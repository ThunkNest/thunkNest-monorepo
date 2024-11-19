package com.validate.monorepo.reputationservice.listener;

import com.validate.monorepo.commonlibrary.model.event.VoteEvent;
import com.validate.monorepo.reputationservice.service.ReputationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VoteEventListener {
	
	private final ReputationService reputationService;
	
	public VoteEventListener(ReputationService reputationService) {
		this.reputationService = reputationService;
	}
	
	@RabbitListener(queues = "q.upVotes")
	public void handleUpVoteEvent(VoteEvent event) {
		log.info("handleUpVoteEvent: received upVote event");
		switch (event.type()) {
			case POST:
				reputationService.postUpVote(event.resourceId(), event.voterId());
				break;
			case REPLY:
				reputationService.replyUpVote(event.resourceId(), event.voterId());
				break;
			default:
				throw new IllegalArgumentException("Unexpected ResourceType: " + event.type());
		}
	}
	
	@RabbitListener(queues = "q.downVotes")
	public void handleDownVoteEvent(VoteEvent event) {
		log.info("handleDownVoteEvent: received downVote event");
		switch (event.type()) {
			case POST:
				reputationService.postDownVote(event.resourceId(), event.voterId());
				break;
			case REPLY:
				reputationService.replyDownVote(event.resourceId(), event.voterId());
				break;
			default:
				throw new IllegalArgumentException("Unexpected ResourceType: " + event.type());
		}
	}
	
}
