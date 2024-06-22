package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

//@Getter
//@Setter
@Data
@Schema(
    description = "PostDto model Information"
)
public class PostDto {
    private Long id;

    @Schema(
        description = "Blog Post Title , title should least have Tow charactors"
    )
    @NotEmpty
    @Size(min = 2, message = "Title should least Tow characters ")
    private String title;


    @Schema(
        description = "Blog Post description , description should least have ten charactors"
    )
    @NotEmpty
    @Size(min = 10, message = "Description should least Ten characters ")
    private String description;


    @Schema(
        description = "Blog Post Content , content should not be emptey"
    )
    @NotEmpty
    private String content;

    private Long categoryId;

    private Set<CommentDto> comments;
}
