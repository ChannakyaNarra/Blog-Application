package com.clueper.blogapp.repository;

import com.clueper.blogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Custom query methods can be defined here if needed

}
