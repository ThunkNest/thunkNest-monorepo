package com.validate.monorepo.commonconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class MQConfig {
	
	@Bean
	public Queue upVoteQueue() {
		return new Queue("q.upVotes", true);
	}
	
	@Bean
	public TopicExchange upVoteExchange() {
		return new TopicExchange("x.upVotes");
	}
	
	@Bean
	public Binding upVoteBinding() {
		return BindingBuilder.bind(upVoteQueue()).to(upVoteExchange()).with("upVotes.#");
	}
	
	@Bean
	public Queue downVoteQueue() {
		return new Queue("q.downVotes", true);
	}
	
	@Bean
	public TopicExchange downVoteExchange() {
		return new TopicExchange("x.downVotes");
	}
	
	@Bean
	public Binding downVoteBinding() {
		return BindingBuilder.bind(downVoteQueue()).to(downVoteExchange()).with("downVotes.#");
	}
	
	@Bean
	public Queue upVoteDLQ() {
		return new Queue("q.upVotes.dlq", true);
	}
	
	@Bean
	public Binding upVoteDLQBinding() {
		return BindingBuilder.bind(upVoteDLQ()).to(upVoteExchange()).with("upVotes.dlq");
	}
	
	@Bean
	public Queue downVoteDLQ() {
		return new Queue("q.downVotes.dlq", true);
	}
	
	@Bean
	public Binding downVoteDLQBinding() {
		return BindingBuilder.bind(upVoteDLQ()).to(upVoteExchange()).with("downVotes.dlq");
	}
	
	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		return rabbitTemplate;
	}
}
