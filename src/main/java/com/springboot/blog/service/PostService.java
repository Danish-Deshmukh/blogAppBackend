package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int PageSize, int PageNo, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updateById(Long id, PostDto postDto);

    void deleteById(Long id);

    List<PostDto> getPostByCategory(Long categoryId);

}
