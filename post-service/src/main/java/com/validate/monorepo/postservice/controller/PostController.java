package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.commonlibrary.model.post.PostRequest;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.util.BlankUtils;
import com.validate.monorepo.postservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public Post createPost(@RequestBody PostRequest postRequest) {
		return postService.createPost(postRequest);
	}
	
	@GetMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Get post by ID",
			description = "Retrieve a post by its unique ID even if the post is deleted.")
	public Post getPostById(@PathVariable String postId) {
		BlankUtils.validateBlank(postId);
		return postService.getPostById(postId);
	}
	
	@PutMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Update a post by ID",
			description = "Update a post by its unique ID.")
	public Post updatePostById(@PathVariable String postId, @RequestBody PostRequest request) {
		BlankUtils.validateBlank(postId);
		return postService.updatePost(postId, request);
	}
	
	@GetMapping("/author/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Get all posts by author",
			description = "Retrieve all posts created by a specific author.")
	public List<Post> getAllPostsByAuthor(@PathVariable String userId) {
		BlankUtils.validateBlank(userId);
		return postService.getAllPostsByAuthor(userId);
	}
	
	@GetMapping("/user/{userId}/interacted")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Get all posts user interacted with",
			description = "Retrieve all posts that a user has interacted with, including upvotes, downvotes, replies, and " +
					"created posts. This endpoint excludes deleted posts.")
	public List<Post> getAllPostsUserInteractedWith(@PathVariable String userId) {
		BlankUtils.validateBlank(userId);
		return postService.getAllPostsUserInteractedWith(userId);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Get all posts",
			description = "Retrieve all posts. This endpoint excludes deleted posts.")
	public List<Post> getAllPosts() {
		return postService.getAllPosts();
	}
	
	@DeleteMapping("/{postId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(
			summary = "Delete a post",
			description = "Delete a post by its unique ID.")
	public void deletePost(@PathVariable String postId) {
		BlankUtils.validateBlank(postId);
		postService.deletePost(postId);
	}
}
