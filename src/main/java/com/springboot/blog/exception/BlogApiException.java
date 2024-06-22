package com.springboot.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException{

    @Getter
    private HttpStatus status;
    private String message;

    public BlogApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
    public BlogApiException(HttpStatus status, String message1) {
        this.status = status;
        this.message = message1;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
