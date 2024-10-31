package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.commonlibrary.model.post.Comment;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.commonlibrary.model.post.PostDto;
import com.validate.monorepo.postservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    @Operation(
        summary = "Add a comment to a post",
        description = "Adds a single comment to an existing post identified by postId"
    )
    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Post addComment(@PathVariable String postId, @RequestBody Comment comment) {
        return postService.addComment(postId, comment);
    }

    @Operation(
        summary = "Add multiple comments to a post",
        description = "Adds a batch of comments to an existing post identified by postId"
    )
    @PostMapping("/{postId}/comments/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public Post addComments(@PathVariable String postId, @RequestBody List<Comment> comments) {
        return postService.addComments(postId, comments);
    }

    @Operation(
        summary = "Like a post",
        description = "Increments the like counter for the post identified by postId"
    )
    @PostMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.OK)
    public Post likePost(@PathVariable String postId) {
        return postService.likePost(postId);
    }

    @Operation(
        summary = "Get posts by author",
        description = "Retrieves all posts created by the specified author"
    )
    @GetMapping("/author/{author}")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getPostsByAuthor(@PathVariable String author) {
        return postService.getPostsByAuthor(author);
    }

    @Operation(
        summary = "Get posts within a time range",
        description = "Retrieves all posts created between the specified start and end times (in epoch milliseconds)"
    )
    @GetMapping("/range")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getPostsByRange(
            @RequestParam long startTime,
            @RequestParam long endTime) {
        return postService.getPostsByRange(startTime, endTime);
    }

    @Operation(
        summary = "Get random posts",
        description = "Retrieves 100 random posts from the database"
    )
    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getRandom100() {
        return postService.getRandom100();
    }
} 