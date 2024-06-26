package com.springboot.blog.controller;

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

    @Value("${project.poster}")
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
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
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
    public ResponseEntity<PostDto> updateById(@PathVariable("id") Long id,@Valid @RequestBody PostDto postDto) {
        PostDto newPostDto = postService.updateById(id, postDto);
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
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        postService.deleteById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }
}
