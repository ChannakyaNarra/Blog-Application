package com.clueper.blogapp.repository;

import com.clueper.blogapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Custom query methods can be defined here if needed
}
