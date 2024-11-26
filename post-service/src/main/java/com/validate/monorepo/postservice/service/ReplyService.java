package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.auth.OauthProvider;
import com.validate.monorepo.commonlibrary.model.reply.CreateReplyRequest;
import com.validate.monorepo.commonlibrary.model.post.neo4j.Post;
import com.validate.monorepo.commonlibrary.model.reply.neo4j.Reply;
import com.validate.monorepo.commonlibrary.model.user.neo4j.User;
import com.validate.monorepo.commonlibrary.repository.neo4j.ReplyRepository;
import com.validate.monorepo.commonlibrary.repository.neo4j.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReplyService {
	
	private final ReplyRepository replyRepository;
	private final PostService postService;
	private final UserRepository userRepository;
	
	@Autowired
	public ReplyService(ReplyRepository replyRepository, PostService postService, UserRepository userRepository) {
		this.replyRepository = replyRepository;
		this.postService = postService;
		this.userRepository = userRepository;
	}
	
//	@Transactional
//	public void addReplyToReply(UUID parentReplyId, Reply reply) {
//		Reply newReply = createReply(reply);
//		replyRepository.addReplyToReply(parentReplyId, newReply.id());
//	}
	
	@Transactional
	public void upVoteReply(UUID replyId, UUID userId) {
		replyRepository.upVoteReply(replyId, userId);
	}
	
	@Transactional
	public void removeUpVoteReply(UUID replyId, UUID userId) {
		replyRepository.removeUpVoteReply(replyId, userId);
	}
	
	@Transactional
	public void downVoteReply(UUID replyId, UUID userId) {
		replyRepository.downVoteReply(replyId, userId);
	}
	
	@Transactional
	public void removeDownVoteReply(UUID replyId, UUID userId) {
		replyRepository.removeDownVoteReply(replyId, userId);
	}
	
	@Transactional(readOnly = true)
	public Reply getReplyById(UUID replyId) {
		return replyRepository.findById(replyId).orElseThrow(() ->
				new NotFoundException(String.format("Reply with ID=%s not found", replyId)));
	}
	
	@Transactional(readOnly = true)
	public List<Reply> getAllReplies() {
		return replyRepository.findAll();
	}
	
	public Reply createReply(Reply reply) {
		return replyRepository.save(reply);
	}
	
	@Transactional
	public Reply createReply(UUID postId, CreateReplyRequest request) {
		User user = new User(UUID.randomUUID(), "ugly-monster-123", OauthProvider.GOOGLE, "12345",
				11232, "fake@gmail.com", null, List.of(), List.of(), LocalDateTime.now());
		
		return new Reply(UUID.randomUUID(), request.replyText(), 0, 0, user, List.of(), List.of(),
				List.of(), null, LocalDateTime.now());
	}
//	public Reply createReply(UUID postId, CreateReplyRequest request) {
//		Post post = postService.getPostById(postId);
//		Reply createdReply = createReply(post, request);
//		postService.addReplyToPost(postId, createdReply.id());
//
//		return createdReply;
//	}

	private Reply createReply(Post post, CreateReplyRequest request) {
		User author = userRepository.findByUsername(request.authorUsername())
				.orElseThrow(() -> new NotFoundException("Author does not exist"));
		
		Reply reply = new Reply(null, request.replyText(), 0, 0, author, List.of(),
				List.of(), List.of(), post, LocalDateTime.now());
		return replyRepository.save(reply);
	}
	
  @Transactional
	public void deleteReply(UUID replyId) {
		replyRepository.deleteById(replyId);
	}
	
}
