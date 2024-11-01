package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.post.Comment;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.post.PostDto;
import com.validate.monorepo.commonlibrary.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
	
	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Post createPost(PostDto dto) {
		Post newPost = new Post(null, dto.getTitle(), dto.getDescription(), 0, new ArrayList<>(),
				dto.getAuthor(), Instant.now().toEpochMilli());
		
		return postRepository.save(newPost);
	}
	
	public Post addCommentToPost(String postId, Comment comment) {
		Comment sanitizedComment = sanitizeComment(comment);
		Post post = postRepository.findById(postId).orElseThrow(() ->
				new BadRequestException("Cannot add comment to post that does not exist"));
		return postRepository.save(post.addReply(sanitizedComment));
	}
	
	public Post addComments(String postId, List<Comment> comments) {
		Post post = postRepository.findById(postId).orElseThrow(() ->
				new BadRequestException("Cannot add comment to post that does not exist"));
		List<Comment> sanitizedComments = comments.stream()
				.map(this::sanitizeComment)
				.toList();
		return postRepository.save(post.addReplies(sanitizedComments));
	}
	
	public Post likePost(String postId) {
		Post post = postRepository.findById(postId).orElseThrow(() ->
				new BadRequestException("Cannot like a post that does not exist"));
		return postRepository.save(post.likePost());
	}
	
	public List<Post> getPostsByAuthor(String author) {
		return postRepository.findByAuthor(author);
	}
	
	public List<Post> getPostsByRange(long startEpochMilli, long endEpochMilli) {
		return postRepository.findByCreatedAtBetween(startEpochMilli, endEpochMilli);
	}
	
	public List<Post> getRandom100() {
		return postRepository.find100RandomPosts();
	}
	
	public Post getPostById(String id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("Post not found"));
	}
	
	public Post addReplyToComment(String postId, String commentId, Comment reply) {
		Comment sanitizedReply = sanitizeComment(reply);
		return postRepository.addReplyToComment(postId, commentId, sanitizedReply)
				.orElseThrow(() -> new NotFoundException("Post or comment not found"));
	}
	
	private Comment sanitizeComment(Comment comment) {
		return new Comment(UUID.randomUUID().toString(), comment.text(), comment.author(), Instant.now().toEpochMilli(),
				0, new ArrayList<>());
	}
	
}
