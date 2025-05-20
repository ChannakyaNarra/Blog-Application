package com.clueper.blogapp.controller;

import com.clueper.blogapp.payload.CommentDto;
import com.clueper.blogapp.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Endpoint to create a new comment
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable(value = "postId") Long postId) {
        // Call the service to create the comment and return the response

        CommentDto createdComment = commentService.createComment(commentDto, postId);
        return ResponseEntity.status(201).body(createdComment);
    }

    // Endpoint to get all comments for a specific post
    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        // Call the service to get comments by post ID and return the response
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // Endpoint to get a specific comment by post ID and comment ID
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId) {
        // Call the service to get the comment by post ID and comment ID and return the response
        CommentDto comment = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(comment);
    }
}
