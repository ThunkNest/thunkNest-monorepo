package com.validate.monorepo.commonlibrary.rabbitmq;

import com.validate.monorepo.commonlibrary.exception.RabbitPublisherException;
import com.validate.monorepo.commonlibrary.model.ampq.EventMessage;
import com.validate.monorepo.commonlibrary.model.event.VoteEvent;
import lombok.extern.slf4j.Slf4j;
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
	
	public void publishUpVoteEvent(VoteEvent event) {
		log.info("publishUpVoteEvent: Publishing upVote event");
		rabbitTemplate.convertAndSend("x.upVote", "upVotes.#", event);
	}
	
	public void publishDownVoteEvent(VoteEvent event) {
		log.info("publishDownVoteEvent: Publishing downVote event");
		rabbitTemplate.convertAndSend("x.downVote", "downVotes.#", event);
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
