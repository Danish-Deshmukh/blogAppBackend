package com.springboot.blog.controller;

import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
    name = "CRUD Rest apis for Authentication resource"
)
public class AuthController {

    private AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @Operation(
        summary = "LOGIN or SIGNIN REST API",
        description = "This REST API is for log-in"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Http status 201 created"
    )
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
        JWTAuthResponse token = authService.login(loginDto);

//        JWTAuthResponse authResponse = new JWTAuthResponse(token);

        return ResponseEntity.ok(token);
    }


    @Operation(
        summary = "REGISTER or SIGNUP REST API",
        description = "This REST API is for registering the use into the database"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Http status 201 created"
    )
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
