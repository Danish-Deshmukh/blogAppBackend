package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;
import java.util.Set;

public interface CommentService {
     CommentDto createComment(Long postId, CommentDto commentDto);
     List<CommentDto> getAllComments(Long postId);
     CommentDto getCommentById(Long postId, Long commentId);
     CommentDto updateById(Long postId, Long commentId, CommentDto commentDto);
     String deleteById(Long postId, Long commentId);

}
