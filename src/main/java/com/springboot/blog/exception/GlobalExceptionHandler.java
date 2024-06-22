package com.springboot.blog.exception;

import com.springboot.blog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFountException.class)
    public ResponseEntity<ErrorDetails> handleRequestNotFoundException(ResourceNotFountException exception,
                                                                       WebRequest webRequest) {

        ErrorDetails details = new ErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException exception,
                                                                       WebRequest webRequest) {

        ErrorDetails details = new ErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    // Handling Global level exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                               WebRequest webRequest) {

        ErrorDetails details = new ErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This is the Validation checking exception
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {


        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
           String fieldError = ((FieldError)error).getField();
           String message = error.getDefaultMessage();

           errors.put(fieldError,message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    // This is the another way of Making Validation exceptions
//    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
//                                                                        WebRequest webRequest) {
//
//        Map<String,String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldValue = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//
//            errors.put(fieldValue,message);
//        });
//        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
//    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
                                                                    WebRequest webRequest) {

        ErrorDetails details = new ErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
    }
}
