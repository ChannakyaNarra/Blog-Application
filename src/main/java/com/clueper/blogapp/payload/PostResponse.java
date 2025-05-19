package com.clueper.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    List<PostDto> content;
    int pageNumber;
    int pageSize;
    long totalElements;
    boolean LastPage;
    int totalPages;
    int numberOfElements;
    boolean FirstPage;
    boolean Empty;
    boolean hasNext;
    boolean hasContent;
    boolean hasPrevious;
}
