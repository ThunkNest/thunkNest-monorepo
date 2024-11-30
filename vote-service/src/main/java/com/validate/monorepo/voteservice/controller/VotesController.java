package com.validate.monorepo.voteservice.controller;

import com.validate.monorepo.commonlibrary.model.vote.VoteRequest;
import com.validate.monorepo.voteservice.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vote")
public class VotesController {
	
	private final VoteService voteService;
	
	@Autowired
	public VotesController(VoteService voteService) {
		this.voteService = voteService;
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Add a vote", description = "Vote on a post or reply.")
	public void addVote(@RequestBody VoteRequest request) {
		voteService.addVote(request);
	}
	
	@DeleteMapping("")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Remove a vote", description = "Remove vote on a post or reply.")
	public void removeVote(@RequestBody VoteRequest request) {
		voteService.removeVote(request);
	}
	
}
