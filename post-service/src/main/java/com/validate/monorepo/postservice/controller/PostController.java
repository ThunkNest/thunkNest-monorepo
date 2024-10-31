package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.commonlibrary.model.post.Comment;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.post.PostDto;
import com.validate.monorepo.postservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
        summary = "Create a new post",
        description = "Creates a new post using the provided post data transfer object"
    )
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.createPost(postDto));
    }

    @Operation(
        summary = "Add a comment to a post",
        description = "Adds a single comment to an existing post identified by postId"
    )
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Post> addComment(@PathVariable String postId, @RequestBody Comment comment) {
        return ResponseEntity.ok(postService.addComment(postId, comment));
    }

    @Operation(
        summary = "Add multiple comments to a post",
        description = "Adds a batch of comments to an existing post identified by postId"
    )
    @PostMapping("/{postId}/comments/batch")
    public ResponseEntity<Post> addComments(@PathVariable String postId, @RequestBody List<Comment> comments) {
        return ResponseEntity.ok(postService.addComments(postId, comments));
    }

    @Operation(
        summary = "Like a post",
        description = "Increments the like counter for the post identified by postId"
    )
    @PostMapping("/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable String postId) {
        return ResponseEntity.ok(postService.likePost(postId));
    }

    @Operation(
        summary = "Get posts by author",
        description = "Retrieves all posts created by the specified author"
    )
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Post>> getPostsByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(postService.getPostsByAuthor(author));
    }

    @Operation(
        summary = "Get posts within a time range",
        description = "Retrieves all posts created between the specified start and end times (in epoch milliseconds)"
    )
    @GetMapping("/range")
    public ResponseEntity<List<Post>> getPostsByRange(
            @RequestParam long startTime,
            @RequestParam long endTime) {
        return ResponseEntity.ok(postService.getPostsByRange(startTime, endTime));
    }

    @Operation(
        summary = "Get random posts",
        description = "Retrieves 100 random posts from the database"
    )
    @GetMapping("/random")
    public ResponseEntity<List<Post>> getRandom100() {
        return ResponseEntity.ok(postService.getRandom100());
    }
} 