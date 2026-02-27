package com.example.library.management.controllers;

import com.example.library.management.dtos.books.User;
import com.example.library.management.entities.UserEntity;
import com.example.library.management.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.library.management.dtos.responses.BaseResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register user
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<User>> register(@RequestBody UserEntity user) {
        return userService.registerUser(user);
    }
}

