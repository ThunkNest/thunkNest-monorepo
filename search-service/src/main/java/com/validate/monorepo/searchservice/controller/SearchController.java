package com.validate.monorepo.searchservice.controller;

import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.searchservice.service.PostSyncService;
import com.validate.monorepo.searchservice.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Search Controller", description = "APIs for searching posts")
@RequestMapping("/api/v1/search")
public class SearchController {
	
	private final SearchService searchService;
	private final PostSyncService postSyncService;
	
	@Autowired
	public SearchController(SearchService searchService, PostSyncService postSyncService) {
		this.searchService = searchService;
		this.postSyncService = postSyncService;
	}
	
	@Operation(summary = "Search for posts",
			description = "Search for posts based on a query string with an optional limit for the number of results.")
	@GetMapping("/")
	public List<Post> searchPosts(
			@Parameter(description = "The search string to query posts")
			@RequestParam("query") String query,
			@Parameter(description = "Optional limit for the number of results")
			@RequestParam(value = "limit", required = false) Integer limit) {
		
		// If limit is not provided, default to no limit
		return searchService.searchPosts(query, limit);
	}
	
	@PostMapping("/sync-all")
	public void sync() {
		postSyncService.syncAllPosts();
	}
}
