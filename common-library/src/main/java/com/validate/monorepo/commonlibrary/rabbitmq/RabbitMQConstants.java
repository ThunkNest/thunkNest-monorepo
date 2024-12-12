package com.validate.monorepo.commonlibrary.rabbitmq;

public final class RabbitMQConstants {
	
	private RabbitMQConstants() {
		// Prevent instantiation
	}
	
	// Exchanges
	public static final String EXCHANGE_UPVOTES = "x.upVotes";
	public static final String EXCHANGE_DOWNVOTES = "x.downVotes";
	public static final String EXCHANGE_POSTS = "x.posts";
	
	// Queues
	public static final String QUEUE_USER_UPVOTES = "q.user.upVotes";
	public static final String QUEUE_USER_DOWNVOTES = "q.user.downVotes";
	public static final String QUEUE_POSTANDREPLY_UPVOTES = "q.postAndReply.upVotes";
	public static final String QUEUE_POSTANDREPLY_DOWNVOTES = "q.postAndReply.downVotes";
	public static final String QUEUE_REPUTATION_UPVOTES = "q.reputation.upVotes";
	public static final String QUEUE_REPUTATION_DOWNVOTES = "q.reputation.downVotes";
	public static final String QUEUE_POSTS_UPDATE = "q.posts.update";
	
	// Dead Letter Queues (DLQ)
	public static final String DLQ_UPVOTES = "q.upVotes.dlq";
	public static final String DLQ_DOWNVOTES = "q.downVotes.dlq";
	
	// Routing Keys
	public static final String ROUTING_KEY_POST_UPDATE = "post.update";
	
}
