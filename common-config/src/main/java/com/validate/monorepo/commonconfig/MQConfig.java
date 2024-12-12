package com.validate.monorepo.commonconfig;

import com.validate.monorepo.commonlibrary.rabbitmq.RabbitMQConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
	
	@Value("${spring.rabbitmq.host}")
	private String rabbitHost;
	
	@Value("${spring.rabbitmq.port}")
	private int rabbitPort;
	
	@Value("${spring.rabbitmq.username}")
	private String rabbitUsername;
	
	@Value("${spring.rabbitmq.password}")
	private String rabbitPassword;
	
	@Value("${spring.rabbitmq.virtual-host}")
	private String rabbitVhost;
	
	// Fanout Exchange for upvotes
	@Bean
	public FanoutExchange upVoteExchange() {
		return new FanoutExchange(RabbitMQConstants.EXCHANGE_UPVOTES, true, false);
	}
	
	// Fanout Exchange for downvotes
	@Bean
	public FanoutExchange downVoteExchange() {
		return new FanoutExchange(RabbitMQConstants.EXCHANGE_DOWNVOTES, true, false);
	}
	
	// Direct Exchange for posts
	@Bean
	public DirectExchange postsExchange() {
		return new DirectExchange(RabbitMQConstants.EXCHANGE_POSTS, true, false);
	}
	
	// Queue for UserService
	@Bean
	public Queue userUpVoteQueue() {
		return new Queue(RabbitMQConstants.QUEUE_USER_UPVOTES, true);
	}
	
	@Bean
	public Binding userUpVoteBinding() {
		return BindingBuilder.bind(userUpVoteQueue()).to(upVoteExchange());
	}
	
	@Bean
	public Queue userDownVoteQueue() {
		return new Queue(RabbitMQConstants.QUEUE_USER_DOWNVOTES, true);
	}
	
	@Bean
	public Binding userDownVoteBinding() {
		return BindingBuilder.bind(userDownVoteQueue()).to(downVoteExchange());
	}
	
	// Queue for PostService
	@Bean
	public Queue postUpVoteQueue() {
		return new Queue(RabbitMQConstants.QUEUE_POSTANDREPLY_UPVOTES, true);
	}
	
	@Bean
	public Binding postUpVoteBinding() {
		return BindingBuilder.bind(postUpVoteQueue()).to(upVoteExchange());
	}
	
	@Bean
	public Queue postDownVoteQueue() {
		return new Queue(RabbitMQConstants.QUEUE_POSTANDREPLY_DOWNVOTES, true);
	}
	
	@Bean
	public Binding postDownVoteBinding() {
		return BindingBuilder.bind(postDownVoteQueue()).to(downVoteExchange());
	}
	
	// Queue for ReputationService
	@Bean
	public Queue reputationUpVoteQueue() {
		return new Queue(RabbitMQConstants.QUEUE_REPUTATION_UPVOTES, true);
	}
	
	@Bean
	public Binding reputationUpVoteBinding() {
		return BindingBuilder.bind(reputationUpVoteQueue()).to(upVoteExchange());
	}
	
	@Bean
	public Queue reputationDownVoteQueue() {
		return new Queue(RabbitMQConstants.QUEUE_REPUTATION_DOWNVOTES, true);
	}
	
	@Bean
	public Binding reputationDownVoteBinding() {
		return BindingBuilder.bind(reputationDownVoteQueue()).to(downVoteExchange());
	}
	
	@Bean
	public Queue postsUpdateQueue() {
		return new Queue(RabbitMQConstants.QUEUE_POSTS_UPDATE, true); // Durable queue
	}
	
	@Bean
	public Binding postsUpdateBinding() {
		return BindingBuilder.bind(postsUpdateQueue()).to(postsExchange()).with(RabbitMQConstants.ROUTING_KEY_POST_UPDATE);
	}
	
	// Dead Letter Queues
	@Bean
	public Queue upVoteDLQ() {
		return new Queue(RabbitMQConstants.DLQ_UPVOTES, true);
	}
	
	@Bean
	public Queue downVoteDLQ() {
		return new Queue(RabbitMQConstants.DLQ_DOWNVOTES, true);
	}
	
	// RabbitAdmin for managing the queues and exchanges
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
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
	
	@PostConstruct
	public void logConnectionDetails() {
		System.out.println("RabbitMQ Configuration:");
		System.out.println("Host: " + rabbitHost);
		System.out.println("Port: " + rabbitPort);
		System.out.println("Username: " + rabbitUsername);
		System.out.println("VHost: " + rabbitVhost);
	}
	
}
