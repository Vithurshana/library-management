package com.example.library.management.dtos.books;

import com.example.library.management.entities.UserEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    public User(UserEntity entity){
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
        this.role = entity.getRole();
        this.createdAt = entity.getCreatedAt();
    }
}
