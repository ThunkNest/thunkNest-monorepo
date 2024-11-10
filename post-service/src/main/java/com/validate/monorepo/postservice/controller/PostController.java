package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.commonlibrary.model.post.CreatePostRequest;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.postservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
	
	private final PostService postService;
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(
			summary = "Create a new post",
			description = "Create a new post with the given title, description, and author ID.")
	public Post createPost(@RequestBody CreatePostRequest createPostRequest) {
		return postService.createPost(createPostRequest);
	}
	
	@GetMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Get post by ID",
			description = "Retrieve a post by its unique ID.")
	public Post getPostById(@PathVariable UUID postId) {
		return postService.getPostById(postId);
	}
	
	@GetMapping("/author/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Get all posts by author",
			description = "Retrieve all posts created by a specific author.")
	public List<Post> getAllPostsByAuthor(@PathVariable UUID userId) {
		return postService.getAllPostsByAuthor(userId);
	}
	
	@GetMapping("/user/{userId}/interacted")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Get all posts user interacted with",
			description = "Retrieve all posts that a user has interacted with, including upvotes, downvotes, replies, and created posts.")
	public List<Post> getAllPostsUserInteractedWith(@PathVariable UUID userId) {
		return postService.getAllPostsUserInteractedWith(userId);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Get all posts",
			description = "Retrieve all posts.")
	public List<Post> getAllPosts() {
		return postService.getAllPosts();
	}
	
	@DeleteMapping("/{postId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(
			summary = "Delete a post",
			description = "Delete a post by its unique ID.")
	public void deletePost(@PathVariable UUID postId) {
		postService.deletePost(postId);
	}
}
