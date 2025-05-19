// src/main/java/com/clueper/blogapp/service/impl/PostServiceImpl.java
package com.clueper.blogapp.service.impl;

import com.clueper.blogapp.entity.Post;
import com.clueper.blogapp.exception.ResourceNotFoundException;
import com.clueper.blogapp.payload.PostDto;
import com.clueper.blogapp.repository.PostRepository;
import com.clueper.blogapp.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    // Constructor injection for PostRepository
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Creates a new post and returns the saved post as a DTO
    @Override
    public PostDto createPost(PostDto postDto) {
        // Convert DTO to entity
        Post post = mapToEntity(postDto);
        // Save entity to database
        Post savedPost = postRepository.save(post);
        // Convert saved entity back to DTO
        return mapToDto(savedPost);
    }

    // Retrieves all posts and returns them as a list of DTOs
    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> mapToDto(post)) // Convert each entity to DTO
                .collect(Collectors.toList());
    }

    // Retrieves a post by its ID and returns it as a DTO
    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));
        return mapToDto(post);
    }

    // Updates an existing post and returns the updated post as a DTO
    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        // Find the existing post
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));

        // Update the fields of the existing post
        existingPost.setTitle(postDto.getTitle());
        existingPost.setDescription(postDto.getDescription());
        existingPost.setContent(postDto.getContent());

        // Save the updated post
        Post updatedPost = postRepository.save(existingPost);

        // Convert the updated entity back to DTO
        return mapToDto(updatedPost);
    }

    // Deletes a post by its ID
    @Override
    public void deletePost(Long id) {
        // Check if the post exists
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));
        // Delete the post
        postRepository.delete(post);
    }






    // Helper method to convert PostDto to Post entity
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    // Helper method to convert Post entity to PostDto
    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
}