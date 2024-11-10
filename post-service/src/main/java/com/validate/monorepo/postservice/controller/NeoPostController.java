package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.commonlibrary.model.neo.post.CreatePostRequest;
import com.validate.monorepo.commonlibrary.model.neo.post.Post;
import com.validate.monorepo.postservice.service.NeoPostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/posts")
public class NeoPostController {
	
	private final NeoPostService postService;
	
	@Autowired
	public NeoPostController(NeoPostService postService) {
		this.postService = postService;
	}
	
	@Operation(
			summary = "Create a new post",
			description = "Creates a new post and saves it in the database.")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest request) {
		Post createdPost = postService.createPost(request.title(), request.description(), request.authorId());
		return ResponseEntity.ok(createdPost);
	}
}
