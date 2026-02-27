package com.example.library.management.controllers;

import com.example.library.management.dtos.responses.BaseResponse;
import com.example.library.management.dtos.responses.auth.LoginResponse;
import com.example.library.management.helpers.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestParam String username, @RequestParam String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = jwtUtil.generateToken(username);

        LoginResponse loginResponse = new LoginResponse(token);

        return new BaseResponse<>(HttpStatus.OK.value(), "Login successful", loginResponse);
    }
}