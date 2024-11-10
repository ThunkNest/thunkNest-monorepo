package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.post.CreatePostRequest;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.user.User;
import com.validate.monorepo.commonlibrary.repository.neo4j.PostRepository;
import com.validate.monorepo.commonlibrary.repository.neo4j.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public PostService(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public Post createPost(CreatePostRequest request) {
		User author = userRepository.findById(request.authorId()).orElseThrow(() ->
				new BadRequestException("Author does not exist"));
		
		Post post = new Post(null, request.title(), request.description(), false, 0, 0, author,
				List.of(), List.of(), List.of(), LocalDateTime.now()
		);
		
		return postRepository.save(post);
	}
	
	@Transactional
	public void upVotePost(UUID postId, UUID userId) {
		postRepository.upVotePost(postId, userId);
	}
	
	@Transactional
	public void removeUpVotePost(UUID postId, UUID userId) {
		postRepository.removeUpVote(postId, userId);
	}
	
	@Transactional
	public void downVotePost(UUID postId, UUID userId) {
		postRepository.downVotePost(postId, userId);
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
