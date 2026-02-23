package com.example.library.management.controllers;

import com.example.library.management.Helpers.BaseResponse;
import com.example.library.management.security.JwtUtil;
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
    public BaseResponse login(@RequestParam String username, @RequestParam String password) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));


        String aaa = jwtUtil.generateToken(username);

        BaseResponse a = new BaseResponse();
        a.data = aaa;

        return a;
    }
}