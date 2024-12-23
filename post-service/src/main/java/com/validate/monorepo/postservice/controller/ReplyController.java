package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.commonlibrary.model.reply.ReplyRequest;
import com.validate.monorepo.commonlibrary.model.reply.Reply;
import com.validate.monorepo.commonlibrary.util.BlankUtils;
import com.validate.monorepo.postservice.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		BlankUtils.validateBlank(postId);
		return replyService.createReply(postId, request);
	}
	
	@GetMapping("/{replyId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get reply by ID", description = "Retrieve a reply by its unique ID even if the reply is deleted.")
	public Reply getReplyById(@PathVariable String replyId) {
		BlankUtils.validateBlank(replyId);
		return replyService.getReplyById(replyId);
	}
	
	@GetMapping("/post{postId}/replies")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get replies to a post given it's ID", description = "Retrieve replies to a post by its unique ID." +
			"This endpoint does not return deleted replies")
	public Page<Reply> getRepliesByPostId(@PathVariable String postId,
	                                      @RequestParam(defaultValue = "0") int page,
	                                      @RequestParam(defaultValue = "10") int size) {
		BlankUtils.validateBlank(postId);
		return replyService.getRepliesByPostId(postId, page, size);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get replies by their IDs", description = "Retrieve replies by their unique IDs even if the replies are deleted.")
	public Page<Reply> getRepliesById(@RequestBody List<String> replyIds,
	                                  @RequestParam(defaultValue = "0") int page,
	                                  @RequestParam(defaultValue = "10") int size) {
		return replyService.getRepliesByIds(replyIds, page, size);
	}
	
	@GetMapping("/author/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Get all posts by author",
			description = "Retrieve all posts created by a specific author.")
	public Page<Reply> getAllRepliesByAuthor(@PathVariable String userId,
	                                      @RequestParam(defaultValue = "0") int page,
	                                      @RequestParam(defaultValue = "10") int size) {
		return replyService.getAllPostsByAuthor(userId, page, size);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get all replies", description = "Retrieve all replies. This endpoint does not include deleted" +
			" replies")
	public Page<Reply> getAllReplies(@RequestParam(defaultValue = "0") int page,
	                                 @RequestParam(defaultValue = "10") int size) {
		return replyService.getAllReplies(page, size);
	}
	
	@GetMapping("/tagged/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get all replies user was tagged in", description = "Retrieve all replies a user was tagged in." +
			"This endpoint does not include deleted replies")
	public Page<Reply> getAllRepliesUserWasTaggedIn(@PathVariable String userId,
	                                                @RequestParam(defaultValue = "0") int page,
	                                                @RequestParam(defaultValue = "10") int size) {
		BlankUtils.validateBlank(userId);
		return replyService.findRepliesByTaggedUserId(userId, page, size);
	}
	
	@PostMapping("/{replyId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Update a reply by ID", description = "Update a reply by its unique ID.")
	public Reply updateReplyById(@PathVariable String replyId, @RequestBody ReplyRequest request) {
		BlankUtils.validateBlank(replyId);
		return replyService.editReply(replyId, request);
	}
	
	@DeleteMapping("/{replyId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete a reply", description = "Delete a reply by its unique ID.")
	public void deleteReply(@PathVariable String replyId) {
		BlankUtils.validateBlank(replyId);
		replyService.deleteReply(replyId);
	}

}
