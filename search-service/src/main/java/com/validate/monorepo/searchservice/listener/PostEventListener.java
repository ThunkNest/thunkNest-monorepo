package com.validate.monorepo.searchservice.listener;

import com.validate.monorepo.commonlibrary.model.ampq.EventMessage;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.rabbitmq.RabbitMQConstants;
import com.validate.monorepo.searchservice.service.PostSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostEventListener {
	
	private final PostSyncService postSyncService;
	
	@Autowired
	public PostEventListener(PostSyncService postSyncService) {
		this.postSyncService = postSyncService;
	}
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_POSTS_UPDATE)
	public void handlePostEvent(EventMessage<Post> eventMessage) {
		try {
			Post post = eventMessage.payload();
			if (post.isDeleted()) {
				postSyncService.deletePost(post.id());
			} else {
				postSyncService.indexPost(post);
			}
		} catch (Exception e) {
			log.error("handlePostEvent: failed to handle post event ", e);
			throw new RuntimeException("handlePostEvent: failed to handle post event", e);
		}
	}
}
