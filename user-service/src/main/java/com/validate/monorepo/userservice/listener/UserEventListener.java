package com.validate.monorepo.userservice.listener;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.model.ampq.EventMessage;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.reply.Reply;
import com.validate.monorepo.commonlibrary.model.vote.VoteRequest;
import com.validate.monorepo.commonlibrary.repository.mongo.PostRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.ReplyRepository;
import com.validate.monorepo.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserEventListener {
	
	private final UserService userService;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	
	public UserEventListener(UserService userService, PostRepository postRepository, ReplyRepository replyRepository) {
		this.userService = userService;
		this.postRepository = postRepository;
		this.replyRepository = replyRepository;
	}
	
	@RabbitListener(queues = "q.user.upVotes")
	public void handleUpVote(EventMessage<VoteRequest> eventMessage) {
		log.info("UserEventListener: received UPVOTE: {}", eventMessage);
		
		final VoteRequest payload = eventMessage.payload();
		String authorId = getResourceAuthor(payload);
		if (payload.isRemoval()) userService.removeUpvoteReputationIncrease(authorId);
		else userService.upvoteReputationIncrease(authorId);
	}
	
	@RabbitListener(queues = "q.user.downVotes")
	public void handleDownVote(EventMessage<VoteRequest> eventMessage) {
		log.info("UserEventListener: received DOWNVOTE: {}", eventMessage);
		
		final VoteRequest payload = eventMessage.payload();
		String authorId = getResourceAuthor(payload);
		if (payload.isRemoval()) userService.removeDownVoteReputationDecrease(authorId);
		else userService.downVoteReputationDecrease(authorId);
	}
	
	String getResourceAuthor(VoteRequest payload) {
		if (payload.postId() != null) {
			Post post = postRepository.findById(payload.postId()).orElseThrow(() ->
					new BadRequestException(String.format("Post with id=%s does not exist", payload.postId())));
			return post.author().id();
		} else {
			Reply reply = replyRepository.findById(payload.replyId()).orElseThrow(() ->
					new BadRequestException(String.format("Post with id=%s does not exist", payload.replyId())));
			return reply.author().id();
		}
	}
	
}
