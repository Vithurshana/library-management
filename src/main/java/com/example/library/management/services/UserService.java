package com.example.library.management.services;

import com.example.library.management.dtos.books.User;
import com.example.library.management.dtos.responses.BaseResponse;
import com.example.library.management.entities.UserEntity;
import com.example.library.management.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    //password hashing
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Register User
    public ResponseEntity<BaseResponse<User>> registerUser(UserEntity user) {

        log.info("---registerUser() started---");
        log.info("Attempting to register user with email={}", user.getEmail());

        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                log.warn("Registration failed: email already exists: {}", user.getEmail());
                return new ResponseEntity<>(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "Email already exists", null),HttpStatus.BAD_REQUEST);
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            UserEntity savedUser = userRepository.save(user);
            User userDto = new User(savedUser);

            emailService.sendEmail(
                savedUser.getEmail(),
                "Welcome to Library Management System",
                "Hello " + savedUser.getUsername() + ",\n\nYour account has been created successfully!"
            );

            log.info("User registered successfully with id={}, email={}", savedUser.getId(), savedUser.getEmail());

            log.info("---registerUser() ended---");

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.CREATED.value(), "User registered successfully", userDto),HttpStatus.CREATED);

        }
        catch (Exception e) {

            log.error("Error while registering user", e);

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to register user", null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public BaseResponse<UserEntity> getUserById(Long id) {
        log.info("---getUserById() started---");
        log.info("Fetching user by id={}", id);

        try {
            UserEntity user = userRepository.findById(id).orElse(null);

            if (user == null) {
                log.error("User not found with id={}", id);

                return new BaseResponse<>(HttpStatus.NOT_FOUND.value(), "User not found", null);
            }

            log.debug("User retrieved successfully: id={}, email={}", user.getId(), user.getEmail());

            log.info("---getUserById() ended---");

            return new BaseResponse<>(HttpStatus.OK.value(), "User fetched successfully", user);

        }
        catch (Exception e) {
            log.error("Error while fetching user", e);

            return new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to fetch user", null);
        }
    }
}