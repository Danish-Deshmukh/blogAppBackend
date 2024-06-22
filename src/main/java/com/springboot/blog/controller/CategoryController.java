package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.CategoryService;
import com.springboot.blog.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/categories")
@Tag(
    name = "CRUD Rest apis for Category resource"
)
public class CategoryController {

    private CategoryService categoryService;
    private PostService postService;

    public CategoryController(CategoryService categoryService, 
    PostService postService) {
        this.categoryService = categoryService;
        this.postService = postService;
    }


    @Operation(
        summary = "Create Category REST API",
        description = "Create Category REST API is used to save Category into database"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Http status 201 CREATED"
    )
    @SecurityRequirement(
        name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    // CREATE
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }


    @Operation(
        summary = "Get all Category REST API",
        description = "Getting all the Category from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @GetMapping
    // GET ALL
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    @Operation(
        summary = "Get By id REST API",
        description = "Getting a Category by ID from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @GetMapping("/{id}")
    // GET BY ID
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(name = "id") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }


    @Operation(
        summary = "Update Category REST API",
        description = "Updating the category by Id from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @SecurityRequirement(
        name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    // UPDATE
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(name = "id") Long categoryId,
                                                      @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDto));
    }


    @Operation(
        summary = "DELETE Category REST API",
        description = "Delete category from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @SecurityRequirement(
        name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    // DELETE
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") Long categoryId) {
        return ResponseEntity.ok(categoryService.deleteById(categoryId));
    }

    

    @Operation(
        summary = "GET Post by Category REST APi",
        description = "This REST API is for getting all Post from database with same category"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @GetMapping("/category/{id}")
    // GET ALL post with same category
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable(name = "id") Long categoryId) {

        return ResponseEntity.ok(postService.getPostByCategory(categoryId));
    }
}
