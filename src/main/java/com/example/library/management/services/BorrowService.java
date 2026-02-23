package com.example.library.management.services;

import com.example.library.management.entities.BookEntity;
import com.example.library.management.entities.BorrowEntity;
import com.example.library.management.entities.UserEntity;
import com.example.library.management.repositories.BookRepository;
import com.example.library.management.repositories.BorrowRepository;
import com.example.library.management.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowService(BorrowRepository borrowRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // Borrow Book
    public BorrowEntity borrowBook(Long userId, Long bookId) {

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getStock() <= 0) {
            throw new RuntimeException("Book out of stock");
        }

        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        BorrowEntity borrow = new BorrowEntity();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDateTime.now());

        return borrowRepository.save(borrow);
    }

    // Return Book
//    public BorrowEntity returnBook(Long borrowId) {
//
//        BorrowEntity borrow = borrowRepository.findById(borrowId)
//                .orElseThrow(() -> new RuntimeException("Borrow record not found"));
//
//        if (borrow.getReturnDate() != null) {
//            throw new RuntimeException("Book already returned");
//        }
//
//        borrow.setReturnDate(LocalDateTime.now());
//
//        BookEntity book = borrow.getBook();
//        book.setStock(book.getStock() + 1);
//        bookRepository.save(book);
//
//        return borrowRepository.save(borrow);
//    }

//    public BorrowEntity returnBook(Long borrowId) {
//
////        BorrowEntity borrow = borrowRepository.findById(borrowId)
////                .orElseThrow(() -> new RuntimeException("Borrow record not found"));
//        BorrowEntity borrow = borrowRepository.findById(borrowId)
//                .orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Borrow record not found")
//                );
//
//
//        if (borrow.getReturnDate() != null) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "Book already returned"
//            );
//
//        }
//
//        borrow.setReturnDate(LocalDateTime.now());
//
//        BookEntity book = borrow.getBook();
//        book.setStock(book.getStock() + 1);
//
//        // Save book
//        bookRepository.save(book);
//
//        // Save borrow
//        return borrowRepository.save(borrow);
//    }

    @Transactional
    public BorrowEntity returnBook(Long borrowId) {

        BorrowEntity borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Borrow record not found")
                );

        if (borrow.getReturnDate() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Book already returned"
            );
        }

        borrow.setReturnDate(LocalDateTime.now());

        BookEntity book = borrow.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

        return borrowRepository.save(borrow);
    }
}

