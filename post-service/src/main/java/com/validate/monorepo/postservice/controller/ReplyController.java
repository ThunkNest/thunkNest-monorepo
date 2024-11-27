package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.commonlibrary.model.reply.ReplyRequest;
import com.validate.monorepo.commonlibrary.model.reply.mongo.Reply;
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

@RestController
@RequestMapping("/api/v1/replies")
public class ReplyController {
	
	private final ReplyService replyService;
	
	@Autowired
	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}
	
	@PostMapping("/post/{postId}")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Add reply to a post", description = "Create a reply for a specific post by providing the post ID.")
	public Reply replyToPost(@PathVariable String postId, @RequestBody ReplyRequest request) {
		return replyService.createReply(postId, request);
	}
	
	@GetMapping("/{replyId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get reply by ID", description = "Retrieve a reply by its unique ID even if the reply is deleted.")
	public Reply getReplyById(@PathVariable String replyId) {
		return replyService.getReplyById(replyId);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get all replies", description = "Retrieve all replies. This endpoint does not include deleted" +
			" replies")
	public List<Reply> getAllReplies() {
		return replyService.getAllReplies();
	}
	
	@GetMapping("/tagged/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get all replies user was tagged in", description = "Retrieve all replies a user was tagged in." +
			"This endpoint does not include deleted replies")
	public List<Reply> getAllRepliesUserWasTaggedIn(@PathVariable String userId) {
		return replyService.findRepliesByTaggedUserId(userId);
	}
	
	@PostMapping("/{replyId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Update a reply by ID", description = "Update a reply by its unique ID.")
	public Reply updateReplyById(@PathVariable String replyId, @RequestBody ReplyRequest request) {
		return replyService.editReply(replyId, request);
	}
	
	@DeleteMapping("/{replyId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete a reply", description = "Delete a reply by its unique ID.")
	public void deleteReply(@PathVariable String replyId) {
		replyService.deleteReply(replyId);
	}

}
