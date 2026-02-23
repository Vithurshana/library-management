package com.example.library.management.services;

import com.example.library.management.entities.UserEntity;
import com.example.library.management.repositories.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        log.info("Authentication attempt for username={}", username);

        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                log.error("User not found during authentication. username={}", username);
                return new UsernameNotFoundException("User not found");
        });

        log.debug("User found in database. username={}, userId={}", user.getUsername(), user.getId());

        return User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles("USER")
            .build();
    }
}