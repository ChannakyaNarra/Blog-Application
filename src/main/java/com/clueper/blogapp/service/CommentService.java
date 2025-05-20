package com.clueper.blogapp.service;

import com.clueper.blogapp.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId);
}
