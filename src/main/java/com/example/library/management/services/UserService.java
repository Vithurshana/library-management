package com.example.library.management.services;

import com.example.library.management.entities.UserEntity;
import com.example.library.management.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public UserEntity registerUser(UserEntity user) {
//
//        if (userRepository.existsByEmail(user.getEmail())) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        return userRepository.save(user);
//    }

    //password hashing
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntity registerUser(UserEntity user) {
        log.info("Attempting to register user with email={}", user.getEmail());

        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("Registration failed: email already exists: {}", user.getEmail());
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        log.info("User registered successfully with id={}, email={}", savedUser.getId(), savedUser.getEmail());

        return savedUser;
    }

    public UserEntity getUserById(Long id) {
        log.info("Fetching user by id={}", id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id={}", id);
                    return new RuntimeException("User not found");
                });
        log.debug("User retrieved successfully: id={}, email={}", user.getId(), user.getEmail());
        return user;
    }
}