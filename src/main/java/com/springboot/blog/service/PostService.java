package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, MultipartFile file) throws IOException;

    PostResponse getAllPost(int PageSize, int PageNo, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updateById(Long id, PostDto postDto);

    void deleteById(Long id);

    List<PostDto> getPostByCategory(Long categoryId);

}
