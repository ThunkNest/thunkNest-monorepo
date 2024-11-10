package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.post.Reply;
import com.validate.monorepo.commonlibrary.repository.neo4j.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReplyService {
	
	private final ReplyRepository replyRepository;
	private final PostService postService;
	
	@Autowired
	public ReplyService(ReplyRepository replyRepository, PostService postService) {
		this.replyRepository = replyRepository;
		this.postService = postService;
	}
	
	@Transactional
	public void addReplyToReply(UUID parentReplyId, Reply reply) {
		Reply newReply = createReply(reply);
		replyRepository.addReplyToReply(parentReplyId, newReply.id());
	}
	
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
	public Reply createReply(UUID postId, Reply reply) {
		Reply createdReply = createReply(reply);
		postService.addReplyToPost(postId, createdReply.id());
		
		return createdReply;
	}
	
	@Transactional
	public void deleteReply(UUID replyId) {
		replyRepository.deleteById(replyId);
	}
}
