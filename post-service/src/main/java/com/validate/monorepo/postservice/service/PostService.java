package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.event.ResourceType;
import com.validate.monorepo.commonlibrary.model.event.VoteEvent;
import com.validate.monorepo.commonlibrary.model.post.CreatePostRequest;
import com.validate.monorepo.commonlibrary.model.post.mongo.Post;
import com.validate.monorepo.commonlibrary.model.user.mongo.User;
import com.validate.monorepo.commonlibrary.rabbitmq.EventPublisher;
import com.validate.monorepo.commonlibrary.repository.mongo.PostRepository;
import com.validate.monorepo.commonlibrary.repository.mongo.UserRepository;
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
	private final EventPublisher eventPublisher;
	
	@Autowired
	public PostService(PostRepository postRepository, UserRepository userRepository, EventPublisher eventPublisher) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.eventPublisher = eventPublisher;
	}
	
	@Transactional
	public Post createPost(CreatePostRequest request) {
		User author = userRepository.findById(request.authorUserId()).orElseThrow(() ->
				new BadRequestException("Author does not exist"));
		
		Post post = new Post(null, request.title(), request.description(), false, 0,
				0, request.openToCoFounder(), author, null, null, null, Instant.now().toEpochMilli()
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
		return postRepository.findAll();
	}
	
	@Transactional
	public Post createPost(Post post) {
		return postRepository.save(post);
	}
	
	@Transactional
	public void deletePost(String postId) {
		Post postToDelete = getPostById(postId);
		postRepository.save(postToDelete.deletePost());
	}
	
	@Transactional
	public void deleteReplyFromPost(String postId, String replyId) {
		Post post = getPostById(postId);
		postRepository.removeReplyFromPost(postId, replyId);
	}
}
