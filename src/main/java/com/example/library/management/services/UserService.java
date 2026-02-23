package com.example.library.management.services;

import com.example.library.management.entities.UserEntity;
import com.example.library.management.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}