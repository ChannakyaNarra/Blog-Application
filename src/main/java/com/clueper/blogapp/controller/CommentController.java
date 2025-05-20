package com.clueper.blogapp.controller;

import com.clueper.blogapp.payload.CommentDto;
import com.clueper.blogapp.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Endpoint to create a new comment
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable(value = "postId") Long postId) {
        // Call the service to create the comment and return the response

        CommentDto createdComment = commentService.createComment(commentDto, postId);
        return ResponseEntity.status(201).body(createdComment);
    }
}
