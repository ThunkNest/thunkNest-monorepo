package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.postservice.service.PostService;
import com.validate.monorepo.postservice.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/votes")
public class VotesController {
	
	private final PostService postService;
	private final ReplyService replyService;
	
	@Autowired
	public VotesController(PostService postService, ReplyService replyService) {
		this.postService = postService;
		this.replyService = replyService;
	}
	
	@PostMapping("/post/{postId}/upVote")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "upVote a post", description = "upVote a post by its unique ID.")
	public void upVotePost(@PathVariable UUID postId, @RequestParam UUID userId) {
		postService.upVotePost(postId, userId);
	}
	
	@PostMapping("/post/{postId}/downVote")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "downVote a post", description = "downVote a post by its unique ID.")
	public void downVotePost(@PathVariable UUID postId, @RequestParam UUID userId) {
		postService.downVotePost(postId, userId);
	}
	
	@PostMapping("/reply/{replyId}/upVote")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "upVote a reply", description = "upVote a reply by its unique ID.")
	public void upVoteReply(@PathVariable UUID replyId, @RequestParam UUID userId) {
		replyService.upVoteReply(replyId, userId);
	}
	
	@PostMapping("/reply/{replyId}/downVote")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "downVote a reply", description = "downVote a reply by its unique ID.")
	public void downVoteReply(@PathVariable UUID replyId, @RequestParam UUID userId) {
		replyService.downVoteReply(replyId, userId);
	}
	
	@DeleteMapping("/post/{postId}/upVote")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Remove upVote from a post", description = "Remove an upVote from a post by its unique ID.")
	public void removeUpVotePost(@PathVariable UUID postId, @RequestParam UUID userId) {
		postService.removeUpVotePost(postId, userId);
	}
	
	@DeleteMapping("/post/{postId}/downVote")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Remove downVote from a post", description = "Remove a downVote from a post by its unique ID.")
	public void removeDownVotePost(@PathVariable UUID postId, @RequestParam UUID userId) {
		postService.removeDownVotePost(postId, userId);
	}
	
	@DeleteMapping("/reply/{replyId}/upVote")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Remove upVote from a reply", description = "Remove an upVote from a reply by its unique ID.")
	public void removeUpVoteReply(@PathVariable UUID replyId, @RequestParam UUID userId) {
		replyService.removeUpVoteReply(replyId, userId);
	}
	
	@DeleteMapping("/reply/{replyId}/downVote")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Remove downVote from a reply", description = "Remove a downVote from a reply by its unique ID.")
	public void removeDownVoteReply(@PathVariable UUID replyId, @RequestParam UUID userId) {
		replyService.removeDownVoteReply(replyId, userId);
	}
}
