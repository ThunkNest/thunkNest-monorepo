package com.validate.monorepo.commonconfig;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
	
	// Fanout Exchange for upvotes
	@Bean
	public FanoutExchange upVoteExchange() {
		return new FanoutExchange("x.upVotes", true, false);
	}
	
	// Fanout Exchange for downvotes
	@Bean
	public FanoutExchange downVoteExchange() {
		return new FanoutExchange("x.downVotes", true, false);
	}
	
	// Queue for UserService
	@Bean
	public Queue userUpVoteQueue() {
		return new Queue("q.user.upVotes", true);
	}
	
	@Bean
	public Binding userUpVoteBinding() {
		return BindingBuilder.bind(userUpVoteQueue()).to(upVoteExchange());
	}
	
	@Bean
	public Queue userDownVoteQueue() {
		return new Queue("q.user.downVotes", true);
	}
	
	@Bean
	public Binding userDownVoteBinding() {
		return BindingBuilder.bind(userDownVoteQueue()).to(downVoteExchange());
	}
	
	// Queue for PostService
	@Bean
	public Queue postUpVoteQueue() {
		return new Queue("q.postAndReply.upVotes", true);
	}
	
	@Bean
	public Binding postUpVoteBinding() {
		return BindingBuilder.bind(postUpVoteQueue()).to(upVoteExchange());
	}
	
	@Bean
	public Queue postDownVoteQueue() {
		return new Queue("q.postAndReply.downVotes", true);
	}
	
	@Bean
	public Binding postDownVoteBinding() {
		return BindingBuilder.bind(postDownVoteQueue()).to(downVoteExchange());
	}
	
	// Queue for ReputationService
	@Bean
	public Queue reputationUpVoteQueue() {
		return new Queue("q.reputation.upVotes", true);
	}
	
	@Bean
	public Binding reputationUpVoteBinding() {
		return BindingBuilder.bind(reputationUpVoteQueue()).to(upVoteExchange());
	}
	
	@Bean
	public Queue reputationDownVoteQueue() {
		return new Queue("q.reputation.downVotes", true);
	}
	
	@Bean
	public Binding reputationDownVoteBinding() {
		return BindingBuilder.bind(reputationDownVoteQueue()).to(downVoteExchange());
	}
	
	// Dead Letter Queues (Optional)
	@Bean
	public Queue upVoteDLQ() {
		return new Queue("q.upVotes.dlq", true);
	}
	
	@Bean
	public Queue downVoteDLQ() {
		return new Queue("q.downVotes.dlq", true);
	}
	
	// RabbitAdmin for managing the queues and exchanges
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}
	
	// Message Converter for JSON Serialization/Deserialization
	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	// RabbitTemplate for publishing messages
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		return rabbitTemplate;
	}
	
}
