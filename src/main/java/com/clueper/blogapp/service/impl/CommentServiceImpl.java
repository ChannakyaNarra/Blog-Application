package com.clueper.blogapp.service.impl;

import com.clueper.blogapp.entity.Comment;
import com.clueper.blogapp.entity.Post;
import com.clueper.blogapp.exception.BlogApiException;
import com.clueper.blogapp.exception.ResourceNotFoundException;
import com.clueper.blogapp.payload.CommentDto;
import com.clueper.blogapp.repository.CommentRepository;
import com.clueper.blogapp.repository.PostRepository;
import com.clueper.blogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    // Dependencies for accessing comment and post data
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // Constructor injection for dependencies
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // Creates a new comment for a given post
    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        // Convert CommentDto to Comment entity
        Comment comment = mapToEntity(commentDto);

        // Find the post by ID or throw exception if not found
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));
        comment.setPost(post); // Associate comment with the post

        // Save the comment entity to the database
        Comment savedComment = commentRepository.save(comment);

        // Convert saved Comment entity back to CommentDto and return
        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        // Find comments by post ID
        List<Comment> comments = commentRepository.findByPostId(postId);

        // Convert each Comment entity to CommentDto
        return comments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // Find the post by ID or throw exception if not found
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));

        // Find comment by ID or throw exception if not found
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(commentId)));
        // Convert Comment entity to CommentDto and return

        // Check if the comment belongs to the post
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }

        return mapToDto(comment);
    }

    // Helper method to convert CommentDto to Comment entity
    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }

    // Helper method to convert Comment entity to CommentDto
    private CommentDto mapToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }
}