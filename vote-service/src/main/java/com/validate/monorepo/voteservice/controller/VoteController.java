package com.validate.monorepo.voteservice.controller;

import com.validate.monorepo.commonlibrary.model.vote.Votes;
import com.validate.monorepo.voteservice.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
public class VoteController {
	
	private final VoteService voteService;
	
	public VoteController(VoteService voteService) {
		this.voteService = voteService;
	}
	
	@Operation(summary = "Upvote a post", description = "Adds the userId to the upVoters set and removes it from the downVoters set.")
	@PostMapping("/upvote/{postId}")
	@ResponseStatus(HttpStatus.OK)
	public void upvotePost(@PathVariable String postId, @RequestParam String userId) {
		voteService.upvotePost(postId, userId);
	}
	
	@Operation(summary = "Downvote a post", description = "Adds the userId to the downVoters set and removes it from the upVoters set.")
	@PostMapping("/downvote/{postId}")
	@ResponseStatus(HttpStatus.OK)
	public void downVotePost(@PathVariable String postId, @RequestParam String userId) {
		voteService.downVotePost(postId, userId);
	}
	
	@Operation(summary = "Get votes for a post", description = "Retrieves the voting information for a specific post.")
	@GetMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	public Votes getVotesForPost(@PathVariable String postId) {
		return voteService.getVotesForPost(postId);
	}
	
	@Operation(summary = "Get upvote count for a post", description = "Retrieves the count of upvotes for a specific post.")
	@GetMapping("/{postId}/upvote-count")
	@ResponseStatus(HttpStatus.OK)
	public long getUpvoteCountForPost(@PathVariable String postId) {
		return voteService.getUpvoteCountForPost(postId);
	}
	
	@Operation(summary = "Get downvote count for a post", description = "Retrieves the count of downvotes for a specific post.")
	@GetMapping("/{postId}/downvote-count")
	@ResponseStatus(HttpStatus.OK)
	public long getDownVoteCountForPost(@PathVariable String postId) {
		return voteService.getDownVoteCountForPost(postId);
	}
}
