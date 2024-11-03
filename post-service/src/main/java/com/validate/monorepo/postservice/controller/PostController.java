package com.validate.monorepo.postservice.controller;

import com.validate.monorepo.commonlibrary.model.post.PostTag;
import com.validate.monorepo.commonlibrary.model.post.Reply;
import com.validate.monorepo.commonlibrary.model.post.Post;
import com.validate.monorepo.postservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @Operation(summary = "Create a new post", description = "Creates a new post and saves it in the database.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }
    
    @Operation(summary = "Get post by ID", description = "Fetches a post by its unique identifier.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post getPostById(@PathVariable String id) {
        return postService.getPostById(id);
    }
    
    @Operation(summary = "Update an existing post", description = "Updates the details of an existing post by its ID.")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post updatePost(@PathVariable String id, @RequestBody Post updatedPost) {
        updatedPost = new Post(id, updatedPost.title(), updatedPost.description(), updatedPost.upVoteCount(),
            updatedPost.downVoteCount(), updatedPost.replyCount(), updatedPost.author(), updatedPost.tag(),
            updatedPost.isDeleted(), updatedPost.createdAt());
        return postService.updatePost(updatedPost);
    }
    
    @Operation(summary = "Delete post by ID", description = "Marks a post as deleted by its unique identifier.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostById(@PathVariable String id) {
        postService.deletePostById(id);
    }
    
    @Operation(summary = "Add a reply to a post", description = "Adds a new reply to the specified post.")
    @PostMapping("/{postId}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public Reply addReplyToPost(@PathVariable String postId, @RequestBody Reply reply) {
        reply = new Reply(null, postId, reply.parentReplyId(), reply.text(), reply.author(), 0, 0, 0, false, reply.createdAt());
        return postService.addReplyToPost(reply);
    }
    
    @Operation(summary = "Tag a post", description = "Adds a tag to the specified post by its ID.")
    @PostMapping("/{postId}/tags")
    @ResponseStatus(HttpStatus.OK)
    public Post tagPost(@PathVariable String postId, @RequestBody PostTag tag) {
        return postService.tagPost(postId, tag);
    }
    
} 
