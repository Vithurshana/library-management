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
@Table(name = "borrow")
@EntityListeners(AuditingEntityListener.class)
public class BorrowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many borrows can belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // Many borrows can belong to one book
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    private LocalDateTime borrowDate;

    private LocalDateTime returnDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    public boolean isReturned(){
//        return returnDate != null;
//    }

    // Constructors
    public BorrowEntity() {}

    public BorrowEntity(UserEntity user, BookEntity book, LocalDateTime borrowDate) {
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
    }
}
