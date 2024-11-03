package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.commonlibrary.model.post.Reply;
import com.validate.monorepo.postservice.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/replies")
public class ReplyController {
	
	private final ReplyService replyService;
	
	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}
	
	@Operation(
			summary = "Get top-level replies for a post",
			description = "Fetches all top-level replies for a given post ID.")
	@GetMapping("/post/{postId}")
	@ResponseStatus(HttpStatus.OK)
	public List<Reply> getTopLevelRepliesByPostId(@PathVariable String postId) {
		return replyService.getTopLevelRepliesByPostId(postId);
	}
	
	@Operation(
			summary = "Get replies by reply ID",
			description = "Fetches replies that are children of the specified reply ID.")
	@GetMapping("/parent/{replyId}")
	@ResponseStatus(HttpStatus.OK)
	public List<Reply> getRepliesByReplyId(@PathVariable String replyId) {
		return replyService.getRepliesByReplyId(replyId);
	}
}
