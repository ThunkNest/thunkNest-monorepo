package com.validate.monorepo.commonlibrary.rabbitmq;

import com.validate.monorepo.commonlibrary.exception.RabbitPublisherException;
import com.validate.monorepo.commonlibrary.model.ampq.EventMessage;
import com.validate.monorepo.commonlibrary.model.vote.VoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventPublisher {
	
	private final RabbitTemplate rabbitTemplate;
	
	public EventPublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void publishVoteEvent(EventMessage<VoteRequest> eventMessage) {
		final VoteRequest payload = eventMessage.payload();
		log.info("publishVoteEvent: attempting to publish vote event with payload={}", payload);
		
		switch (payload.action()) {
			case UPVOTE -> {
				try {
					rabbitTemplate.convertAndSend("x.upVotes", "", eventMessage);
				} catch (AmqpException ex) {
					log.error("Failed to publish message to RabbitMQ", ex);
					throw new RabbitPublisherException("Failed to publish vote event", ex);
				}
			}
			case DOWNVOTE -> {
				try {
					rabbitTemplate.convertAndSend("x.downVotes", "", eventMessage);
				} catch (AmqpException ex) {
					log.error("Failed to publish message to RabbitMQ", ex);
					throw new RabbitPublisherException("Failed to publish vote event", ex);
				}
			}
			default -> throw new IllegalArgumentException("Invalid vote action provided");
		}
	}
	
}
