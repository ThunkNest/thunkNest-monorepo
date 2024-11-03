package com.validate.monorepo.postservice.service;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import com.validate.monorepo.commonlibrary.exception.NotFoundException;
import com.validate.monorepo.commonlibrary.model.post.PostTag;
import com.validate.monorepo.commonlibrary.model.post.Reply;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.repository.PostRepository;
import com.validate.monorepo.commonlibrary.repository.ReplyRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PostService {
	
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	
	public PostService(PostRepository postRepository, ReplyRepository replyRepository) {
		this.postRepository = postRepository;
		this.replyRepository = replyRepository;
	}
	
	public Post createPost(Post post) {
		return postRepository.save(sanitizePost(post));
	}
	
	public Post getPostById(String id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("Post not found"));
	}
	
	public Post updatePost(Post updatedPost) {
		 Post existingPost = postRepository.findById(updatedPost.id())
				.orElseThrow(() -> new NotFoundException("Post not found"));
		 
		 return postRepository.save(updatePostFields(existingPost, updatedPost));
	}
	
	public void deletePostById(String postId) {
		Post postToDelete = verifyPostExists(postId);
		
		postRepository.save(postToDelete.deletePost());
	}
	
	public Reply addReplyToPost(Reply reply) {
		verifyPostExists(reply.postId());
		Reply sanitizedReply = sanitizeReply(reply);
		return replyRepository.save(sanitizedReply);
	}
	
	public Post tagPost(String postId, PostTag tag) {
		Post postToTag = verifyPostExists(postId);
		return postRepository.save(postToTag.tagPost(tag));
	}
	
	private Post verifyPostExists(String postId) {
		if (StringUtils.isBlank(postId)) throw new BadRequestException("Post Id cannot be null or empty");
		return postRepository.findById(postId).orElseThrow(() ->
				new NotFoundException(String.format("Post with ID=%s not found", postId)));
	}
	
	private Post updatePostFields(Post existingPost, Post updatedPost) {
		return new Post(
				existingPost.id(),
				updatedPost.title() != null ? updatedPost.title() : existingPost.title(),
				updatedPost.description() != null ? updatedPost.description() : existingPost.description(),
				existingPost.upVoteCount(),
				existingPost.downVoteCount(),
				existingPost.replyCount(),
				existingPost.author(),
				updatedPost.tag() != null ? updatedPost.tag() : existingPost.tag(),
				existingPost.isDeleted(),
				existingPost.createdAt()
		);
	}
	
	private Reply sanitizeReply(Reply reply) {
		if (StringUtils.isBlank(reply.postId())) throw new BadRequestException("Reply must contain post Id");
		return new Reply(UUID.randomUUID().toString(), reply.postId(), reply.parentReplyId(), reply.text(),
				reply.author(), 0, 0, 0, false, Instant.now().toEpochMilli());
	}
	
	private Post sanitizePost(Post post) {
		return new Post(null, post.title(), post.description(), 0, 0, 0,
				post.author(), null, false, Instant.now().toEpochMilli());
	}
	
}
