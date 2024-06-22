package com.springboot.blog.service;

import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;

public interface AuthService {
    public abstract JWTAuthResponse login(LoginDto loginDto);

    public abstract String register(RegisterDto registerDto);
}
