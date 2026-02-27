package com.example.library.management.dtos.books;

import com.example.library.management.entities.BookEntity;
import com.example.library.management.entities.BorrowEntity;
import com.example.library.management.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

public class Borrow {
    private Long id;
    private User user;
    private Book book;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private LocalDateTime createdAt;

    public Borrow (BorrowEntity entity){
        this.id = entity.getId();
        this.user = new User(entity.getUser());
        this.book = new Book(entity.getBook());
        this.borrowDate = entity.getBorrowDate();
        this.returnDate = entity.getReturnDate();
        this.createdAt = entity.getCreatedAt();
    }
}