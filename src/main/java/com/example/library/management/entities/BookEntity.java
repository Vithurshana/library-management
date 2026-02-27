package com.example.library.management.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Table(name = "book")
@EntityListeners(AuditingEntityListener.class)

public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private Integer stock;

    private String pdfPath;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Constructors
    public BookEntity() {}

    public BookEntity(String title, String author, Integer stock, String pdfPath) {
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.pdfPath = pdfPath;
    }
}