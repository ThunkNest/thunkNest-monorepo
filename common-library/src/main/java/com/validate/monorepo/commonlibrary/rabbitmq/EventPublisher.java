package com.validate.monorepo.commonlibrary.rabbitmq;

import com.validate.monorepo.commonlibrary.exception.RabbitPublisherException;
import com.validate.monorepo.commonlibrary.model.ampq.EventMessage;
import com.validate.monorepo.commonlibrary.model.vote.VoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

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
					rabbitTemplate.convertAndSend("x.upVote", "upVotes.#", payload);
				} catch (AmqpException ex) {
					log.error("Failed to publish message to RabbitMQ", ex);
					throw new RabbitPublisherException("Failed to publish vote event", ex);
				}
			}
			case DOWNVOTE -> {
				try {
					rabbitTemplate.convertAndSend("x.downVote", "downVotes.#", payload);
				} catch (AmqpException ex) {
					log.error("Failed to publish message to RabbitMQ", ex);
					throw new RabbitPublisherException("Failed to publish vote event", ex);
				}
			}
			default -> throw new IllegalArgumentException("Invalid vote action provided");
		}
	}
	
	public void publishMatchEvent2(Object match) {
		log.info("publishMatchEvent: Attempting to publish a match event");
		EventMessage<Object> message = new EventMessage<>(
				UUID.randomUUID().toString(),
				match,
				Instant.now().toEpochMilli()
		);
		
		try {
			rabbitTemplate.convertAndSend("", "q.user-matches", message);
		} catch (Exception e) {
			log.error("publishMatchEvent: Error publishing match event.");
			throw new RabbitPublisherException("Error publishing match event: ", e);
		}
	}
	
}
