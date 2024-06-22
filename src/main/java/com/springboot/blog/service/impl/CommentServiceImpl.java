package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFountException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    // creating comment here
    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        // This is method is for getting the post by Id and if post is not available then throws exception
        Post post = getPostById(postId);

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getAllComments(Long postId) {
        List<Comment> comments = commentRepository.findCommentByPostId(postId);


        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        // This is method is for getting the object by Id and if object is not available then throws exception
        Post post = getPostById(postId);

        // This is method is for getting the object by Id and if object is not available then throws exception
        Comment comment = getCommentById(commentId);

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, " Comment didn't belong to the post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateById(Long postId, Long commentId, CommentDto commentDto) {

        // This is method is for getting the object by Id and if object is not available then throws exception
        Post post = getPostById(postId);

        // This is method is for getting the object by Id and if object is not available then throws exception
        Comment comment = getCommentById(commentId);

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, " Comment didn't belong to the post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDTO(updatedComment);
    }

    @Override
    public String deleteById(Long postId, Long commentId) {


        // This is method is for getting the object by Id and if object is not available then throws exception
        Post post = getPostById(postId);

        // This is method is for getting the object by Id and if object is not available then throws exception
        Comment comment = getCommentById(commentId);

        commentRepository.deleteById(commentId);

        return "Comment Deleted successfully";
    }


    // converting Entity to DTO
    private CommentDto mapToDTO(Comment comment) {
//        CommentDto commentDto = new CommentDto();
//
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    // converting DTO to Entity
    private Comment mapToEntity(CommentDto commentDto) {
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        Comment comment = modelMapper.map(commentDto, Comment.class);

        return comment;
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFountException("Post ", " id", postId));
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFountException("Comment ", " Id ", commentId));
    }
}
