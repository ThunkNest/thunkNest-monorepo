package com.validate.monorepo.voteservice.controller;

import com.validate.monorepo.commonlibrary.model.vote.mongo.VoteType;
import com.validate.monorepo.voteservice.service.VoteUtilityService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/votes/utility")
public class VotesUtilityController {
	
	private final VoteUtilityService service;
	
	public VotesUtilityController(VoteUtilityService service) {
		this.service = service;
	}
	
	@GetMapping("/count/post/{postId}/{voteType}")
	@Operation(
			summary = "Count votes for a post",
			description = "Counts the number of upvotes or downvotes for a specific post based on the provided vote type."
	)
	public long countVotesByPost(@PathVariable String postId, @PathVariable VoteType voteType) {
		return service.countVotesByPost(postId, voteType);
	}
	
	@GetMapping("/count/reply/{replyId}/{voteType}")
	@Operation(
			summary = "Count votes for a reply",
			description = "Counts the number of upvotes or downvotes for a specific reply based on the provided vote type."
	)
	public long countVotesByReply(@PathVariable String replyId, @PathVariable VoteType voteType) {
		return service.countVotesByReply(replyId, voteType);
	}
	
	@GetMapping("/has-voted")
	@Operation(
			summary = "Check if a user has voted",
			description = "Checks if a user has voted on a specific post or reply."
	)
	public boolean hasUserVoted(
			@RequestParam String userId,
			@RequestParam(required = false) String postId,
			@RequestParam(required = false) String replyId
	) {
		return service.hasUserVoted(userId, postId, replyId);
	}
	
	@GetMapping("/all-votes/{userId}")
	@Operation(
			summary = "Get all votes by a user",
			description = "Retrieves all posts or replies that a user has voted on."
	)
	public List<String> findAllVotesByUser(@PathVariable String userId) {
		return service.findAllVotesByUser(userId);
	}
	
	@GetMapping("/votes/{userId}/{voteType}")
	@Operation(
			summary = "Get votes by a user filtered by vote type",
			description = "Retrieves all posts or replies that a user has upvoted or downvoted based on the vote type."
	)
	public List<String> findVotesByUserAndVoteType(@PathVariable String userId, @PathVariable VoteType voteType) {
		return service.findVotesByUserAndVoteType(userId, voteType);
	}
	
}
