package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.commonlibrary.model.post.Reply;
import com.validate.monorepo.postservice.service.ReplyService;
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
@RequestMapping("/api/v1/replies")
public class ReplyController {
	
	private final ReplyService replyService;
	
	@Autowired
	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}
	
	@PostMapping("/{parentReplyId}")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Add reply to a reply", description = "Add a reply to an existing reply by providing the parent reply ID.")
	public void addReplyToReply(@PathVariable UUID parentReplyId, @RequestBody Reply reply) {
		replyService.addReplyToReply(parentReplyId, reply);
	}
	
	@GetMapping("/{replyId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get reply by ID", description = "Retrieve a reply by its unique ID.")
	public Reply getReplyById(@PathVariable UUID replyId) {
		return replyService.getReplyById(replyId);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get all replies", description = "Retrieve all replies.")
	public List<Reply> getAllReplies() {
		return replyService.getAllReplies();
	}
	
	@PostMapping("/post/{postId}")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a reply for a post", description = "Create a reply for a specific post by providing the post ID.")
	public Reply createReply(@PathVariable UUID postId, @RequestBody Reply reply) {
		return replyService.createReply(postId, reply);
	}
	
	@DeleteMapping("/{replyId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete a reply", description = "Delete a reply by its unique ID.")
	public void deleteReply(@PathVariable UUID replyId) {
		replyService.deleteReply(replyId);
	}

}
