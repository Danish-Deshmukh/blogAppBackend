package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(
    name = "CRUD Rest apis for Comment resource"
)
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @Operation(
        summary = "Create Comment REST API",
        description = "Create comment REST API is used to save comment into database"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Http status 201 CREATED"
    )
    @PostMapping("/{postId}/comments")
    // CREATE 
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId,
                                                    @Valid @RequestBody CommentDto commentDto) {


        return new ResponseEntity<>(commentService.createComment(postId,commentDto),HttpStatus.CREATED);
    }


    @Operation(
        summary = "Get all Comment REST API",
        description = "Getting all the comment from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @GetMapping("/{postId}/comments")
    // GET ALL
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(value = "postId") Long postId) {

        return ResponseEntity.ok(commentService.getAllComments(postId));
    }



    @Operation(
        summary = "Get By id REST API",
        description = "Getting a comment by ID from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @GetMapping("/{postsId}/comments/{commentId}")
    // GET BY ID
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postsId") Long postId,
                                                     @PathVariable(value = "commentId") Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId,commentId));
    }


    @Operation(
        summary = "Update Comment REST API",
        description = "Updating the comment by Id from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @PutMapping("/{postId}/comments/{commentId}")
    // UPDATE
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "commentId") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {

        return ResponseEntity.ok(commentService.updateById(postId,commentId,commentDto));
    }


    @Operation(
        summary = "DELETE Comment REST API",
        description = "Delete comment from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 ok"
    )
    @DeleteMapping("/{postId}/comments/{commentId}")
    // DELETE
    public ResponseEntity<String> deleteById(@PathVariable(value = "postId") Long postId,
                                             @PathVariable(value = "commentId") Long commentId) {
        return new ResponseEntity<>(commentService.deleteById(postId,commentId),HttpStatus.OK);
    }
}
