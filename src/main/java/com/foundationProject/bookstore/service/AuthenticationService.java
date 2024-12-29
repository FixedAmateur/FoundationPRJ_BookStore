package com.foundationProject.bookstore.service;

import com.foundationProject.bookstore.model.dto.LoginDto;
import com.foundationProject.bookstore.model.dto.RegisterDto;
import com.foundationProject.bookstore.model.response.JwtAuthResponse;

public interface AuthenticationService {
    JwtAuthResponse login(LoginDto loginDto);

    JwtAuthResponse refreshToken(String refreshToken);

    String register(RegisterDto registerDto);
}
