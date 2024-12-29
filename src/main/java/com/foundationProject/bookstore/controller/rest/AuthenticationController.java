package com.foundationProject.bookstore.controller.rest;

import com.foundationProject.bookstore.service.AuthenticationService;
import com.foundationProject.bookstore.service.JwtService;
import com.foundationProject.bookstore.model.dto.LoginDto;
import com.foundationProject.bookstore.model.dto.RegisterDto;
import com.foundationProject.bookstore.model.response.JwtAuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @Operation(summary = "Login", description = "Login API")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login (@RequestBody @Valid LoginDto loginDto){
        JwtAuthResponse authenticationUser =  authenticationService.login(loginDto);

        return ResponseEntity.ok(authenticationUser);
    }

    @Operation(summary = "Register", description = "Register API")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto){
        String response = authenticationService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}