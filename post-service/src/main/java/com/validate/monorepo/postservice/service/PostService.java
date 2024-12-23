package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.ampq.EventMessage;
import com.validate.monorepo.commonlibrary.model.post.PostRequest;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.model.vote.VoteType;
import com.validate.monorepo.commonlibrary.rabbitmq.EventPublisher;
import com.validate.monorepo.commonlibrary.repository.mongo.PostRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.UserRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final VoteRepository voteRepository;
	private final EventPublisher eventPublisher;
	
	@Autowired
	public PostService(PostRepository postRepository, UserRepository userRepository, VoteRepository voteRepository, EventPublisher eventPublisher) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.voteRepository = voteRepository;
		this.eventPublisher = eventPublisher;
	}
	
	@Transactional
	public Post createPost(PostRequest request) {
		User author = userRepository.findById(request.authorUserId()).orElseThrow(() ->
				new BadRequestException("Author does not exist"));
		
		Post post = new Post(null, request.title(), request.description(), false, 0, false,
				0, 0, 0, request.openToCoFounder(), author, null,
				Instant.now().toEpochMilli()
		);
		
		Post createdPost = postRepository.save(post);
		log.info("Created Post with ID: {}", createdPost.id());
		log.info("Post: {}", createdPost);
		
		eventPublisher.publishPostUpdateEvent(createPostEventMessage(createdPost));
		
		return createdPost;
	}
	
	@Transactional
	public void addReplyToPost(String postId, String replyId) {
		postRepository.addReplyToPost(postId, replyId);
	}
	
	@Transactional(readOnly = true)
	public Post getPostById(String postId) {
		log.info("Fetching Post with ID: {}", postId);
		return postRepository.findById(postId).orElseThrow(() ->
				new NotFoundException(String.format("Post with ID=%s not found", postId)));
	}
	
	@Transactional
	public Post updatePost(String postId, PostRequest request) {
		Post postToUpdate = getPostById(postId);
		
		if (postToUpdate.isDeleted()) throw new BadRequestException("A deleted post cannot be updated");
		
		Post updatedPost = new Post(
				postToUpdate.id(),
				postToUpdate.title(),
				request.description() != null ? request.description() : postToUpdate.description(),
				false,
				postToUpdate.deletedAt(),
				true,
				Instant.now().toEpochMilli(),
				postToUpdate.upVoteCount(),
				postToUpdate.downVoteCount(),
				request.openToCoFounder(),
				postToUpdate.author(),
				postToUpdate.replies(),
				postToUpdate.createdAt()
		);
		
		Post savedUpdatedPost =  postRepository.save(updatedPost);
		eventPublisher.publishPostUpdateEvent(createPostEventMessage(savedUpdatedPost));
		
		return savedUpdatedPost;
	}
	
	@Transactional(readOnly = true)
	public Page<Post> getAllPostsByAuthor(String userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return postRepository.findAllPostsByAuthor(userId, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<Post> getAllPostsUserInteractedWith(String userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return postRepository.findAllPostsUserInteractedWith(userId, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<Post> getAllPosts(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return postRepository.findAllPostsAndIsDeletedFalse(pageable);
		
	}
	
	@Transactional
	public void deletePost(String postId) {
		Post postToDelete = getPostById(postId).deletePost();
		
		postRepository.save(postToDelete);
		eventPublisher.publishPostUpdateEvent(createPostEventMessage(postToDelete));
	}
	
	@Transactional
	public void deleteReplyFromPost(String postId, String replyId) {
		Post post = getPostById(postId);
		postRepository.removeReplyFromPost(postId, replyId);
	}
	
	public void handleVote(String postId) {
		long upVoteCount = voteRepository.countVotesByPost(postId, VoteType.UPVOTE);
		long downVoteCount = voteRepository.countVotesByPost(postId, VoteType.DOWNVOTE);
		
		postRepository.updateVoteCount(postId, upVoteCount, downVoteCount);
	}
	
	private EventMessage<Post> createPostEventMessage(Post postToDelete) {
		return new EventMessage<>(UUID.randomUUID().toString(), postToDelete, Instant.now().toEpochMilli());
	}
	
}
