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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

        log.info("Attempting to borrow book. userId={}, bookId={}", userId, bookId);

        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> {
                log.error("User not found with id={}", userId);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            });

        BookEntity book = bookRepository.findById(bookId)
            .orElseThrow(() -> {
                log.error("Book not found with id={}", bookId);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
            });

        if (book.getStock() <= 0) {
            log.warn("Book out of stock. bookId={}", bookId);
            throw new RuntimeException("Book out of stock");
        }

        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        BorrowEntity borrow = new BorrowEntity();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDateTime.now());

        BorrowEntity savedBorrow = borrowRepository.save(borrow);

        log.info("Book borrowed successfully. borrowId={}, userId={}, bookId={}",
                savedBorrow.getId(), userId, bookId);

        return savedBorrow;
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

        log.info("Attempting to return book. borrowId={}", borrowId);

        BorrowEntity borrow = borrowRepository.findById(borrowId).orElseThrow(() -> {
            log.error("Borrow record not found. borrowId={}", borrowId);
            return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Borrow record not found"
            );
        });

        if (borrow.getReturnDate() != null) {
            log.warn("Book already returned. borrowId={}", borrowId);
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Book already returned"
            );
        }

        borrow.setReturnDate(LocalDateTime.now());

        BookEntity book = borrow.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

        BorrowEntity updatedBorrow = borrowRepository.save(borrow);

        log.info("Book returned successfully. borrowId={}, bookId={}", borrowId, book.getId());

        return updatedBorrow;
    }
}