package com.springboot.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

//    http://localhost:8080/api/v1/posts
@CrossOrigin("*")
@RestController
@RequestMapping
@Tag(
    name = "CRUD Rest apis for Post resource"
)
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Value("${project.image}")
    private String path;


    @Operation(
        summary = "Create Post REST API",
        description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Http status 201 CREATED"
    )
    @SecurityRequirement(
        name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    // CREATE
    public ResponseEntity<PostDto> createPost(@RequestPart MultipartFile file,
                                              @RequestPart String postDto) throws IOException {
        PostDto dto = convertToMovieDto(postDto);
        return new ResponseEntity<>(postService.createPost(dto, file), HttpStatus.CREATED);
    }
    private PostDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, PostDto.class);
    }


    @Operation(
        summary = "Get all posts REST API",
        description = "Getting all the post from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @GetMapping("/api/v1/posts")
    // FOR GETTING ALL POSTS
    public PostResponse getAllPost(
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {

        return postService.getAllPost(pageSize,pageNo,sortBy,sortDir);
    }

    
    @Operation(
        summary = "Get By id REST API",
        description = "Getting a post by ID from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @GetMapping("/api/v1/posts/{id}")
    // GET BY ID
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long pid) {
        return ResponseEntity.ok(postService.getPostById(pid));
    }


    @Operation(
        summary = "Update post REST API",
        description = "Updating the post by Id from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @SecurityRequirement(
        name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    // UPDATE
    public ResponseEntity<PostDto> updateById(@PathVariable("id") Long id,
                                              @RequestPart MultipartFile file,
                                              @RequestPart String postDto
                                              ) throws IOException{
        PostDto dto = convertToMovieDto(postDto);

        PostDto newPostDto = postService.updateById(id, dto, file);
        return ResponseEntity.ok(newPostDto);
    }



    @Operation(
        summary = "DELETE post REST API",
        description = "Delete post from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @SecurityRequirement(
        name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    // DELETE
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) throws IOException {
        postService.deleteById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }
}
