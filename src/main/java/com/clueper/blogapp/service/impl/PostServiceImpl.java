// src/main/java/com/clueper/blogapp/service/impl/PostServiceImpl.java
package com.clueper.blogapp.service.impl;

import com.clueper.blogapp.entity.Post;
import com.clueper.blogapp.exception.ResourceNotFoundException;
import com.clueper.blogapp.payload.PostDto;
import com.clueper.blogapp.payload.PostResponse;
import com.clueper.blogapp.repository.PostRepository;
import com.clueper.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    public ModelMapper mapper;

    // Constructor injection for PostRepository
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.mapper = mapper;
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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // Set the sorting direction
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();

        List<PostDto> content =  postList.stream()
                                        .map(post -> mapToDto(post)) // Convert each entity to DTO
                                        .collect(Collectors.toList());

        // Create a PostResponse object to hold the response data
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLastPage(posts.isLast());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setNumberOfElements(posts.getNumberOfElements());
        postResponse.setFirstPage(posts.isFirst());
        postResponse.setEmpty(posts.isEmpty());
        postResponse.setHasNext(posts.hasNext());
        postResponse.setHasContent(posts.hasContent());
        postResponse.setHasPrevious(posts.hasPrevious());

        return postResponse;
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
        Post post = mapper.map(postDto, Post.class);
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }

    // Helper method to convert Post entity to PostDto
    private PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }
}