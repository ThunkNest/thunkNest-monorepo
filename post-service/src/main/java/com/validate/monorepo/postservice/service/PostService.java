package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.event.ResourceType;
import com.validate.monorepo.commonlibrary.model.event.VoteEvent;
import com.validate.monorepo.commonlibrary.model.post.CreatePostRequest;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.rabbitmq.EventPublisher;
import com.validate.monorepo.commonlibrary.repository.neo4j.PostRepository;
import com.validate.monorepo.commonlibrary.repository.neo4j.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
		User author = userRepository.findById(request.authorId()).orElseThrow(() ->
				new BadRequestException("Author does not exist"));
		
		Post post = new Post(null, request.title(), request.description(), false, 0,
				0, author, null, null, null, LocalDateTime.now()
		);
		
		Post createdPost = postRepository.save(post);
		log.info("Created Post with ID: {}", createdPost.id());
		log.info("Post: {}", createdPost);
		
		return createdPost;
	}
	
	@Transactional
	public void upVotePost(UUID postId, UUID userId) {
		VoteEvent event = new VoteEvent(ResourceType.POST, postId.toString(), userId.toString());
		eventPublisher.publishUpVoteEvent(event);
		postRepository.upVotePost(postId, userId);
	}
	
	@Transactional
	public void removeUpVotePost(UUID postId, UUID userId) {
		postRepository.removeUpVote(postId, userId);
	}
	
	@Transactional
	public void downVotePost(UUID postId, UUID userId) {
		VoteEvent event = new VoteEvent(ResourceType.POST, postId.toString(), userId.toString());
		postRepository.downVotePost(postId, userId);
		eventPublisher.publishDownVoteEvent(event);
	}
	
	@Transactional
	public void removeDownVotePost(UUID postId, UUID userId) {
		postRepository.removeDownVote(postId, userId);
	}
	
	@Transactional
	public void addReplyToPost(UUID postId, UUID replyId) {
		postRepository.addReplyToPost(postId, replyId);
	}
	
	@Transactional(readOnly = true)
	public Post getPostById(UUID postId) {
		log.info("Fetching Post with ID: {}", postId);
		return postRepository.findById(postId).orElseThrow(() ->
				new NotFoundException(String.format("Post with ID=%s not found", postId)));
	}
	
	@Transactional(readOnly = true)
	public List<Post> getAllPostsByAuthor(UUID userId) {
		return postRepository.findAllPostsByAuthor(userId);
	}
	
	@Transactional(readOnly = true)
	public List<Post> getAllPostsUserInteractedWith(UUID userId) {
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
	public void deletePost(UUID postId) {
		Post postToDelete = getPostById(postId);
		postRepository.save(postToDelete.deletePost());
	}
}
