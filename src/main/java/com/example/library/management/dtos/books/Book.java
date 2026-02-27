package com.example.library.management.dtos.books;

import com.example.library.management.entities.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer stock;
    private LocalDateTime createdAt;

    public Book(BookEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.stock = entity.getStock();
        this.createdAt = entity.getCreatedAt();
    }
}
