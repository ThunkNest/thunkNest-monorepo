package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.exception.NotFoundException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

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
		
		return postRepository.save(updatedPost);
	}
	
	@Transactional(readOnly = true)
	public List<Post> getAllPostsByAuthor(String userId) {
		return postRepository.findAllPostsByAuthor(userId);
	}
	
	@Transactional(readOnly = true)
	public List<Post> getAllPostsUserInteractedWith(String userId) {
		return postRepository.findAllPostsUserInteractedWith(userId);
	}
	
	@Transactional(readOnly = true)
	public List<Post> getAllPosts() {
		return postRepository.findAllPostsAndIsDeletedFalse();
	}
	
	@Transactional
	public void deletePost(String postId) {
		getPostById(postId);
		postRepository.save(getPostById(postId).deletePost());
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
}
