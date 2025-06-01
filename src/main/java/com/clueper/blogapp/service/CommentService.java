package com.clueper.blogapp.service;

import com.clueper.blogapp.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(CommentDto commentDto, Long postId, Long commentId);

    void deleteComment(Long postId, Long commentId);
}
