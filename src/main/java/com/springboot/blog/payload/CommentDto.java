package com.springboot.blog.payload;

import com.springboot.blog.entity.Post;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty(message = "Name should Not be empty")
    private String name;

    @NotEmpty(message = "Email should be in proper Email format")
    @Email
    private String email;

    @NotEmpty(message = "Message body should not be empty")
    private String body;

}
