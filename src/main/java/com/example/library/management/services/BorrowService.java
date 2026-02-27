package com.example.library.management.services;

import com.example.library.management.dtos.books.Book;
import com.example.library.management.dtos.books.Borrow;
import com.example.library.management.dtos.responses.BaseResponse;
import com.example.library.management.entities.BookEntity;
import com.example.library.management.entities.BorrowEntity;
import com.example.library.management.entities.UserEntity;
import com.example.library.management.repositories.BookRepository;
import com.example.library.management.repositories.BorrowRepository;
import com.example.library.management.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public BorrowService(BorrowRepository borrowRepository, BookRepository bookRepository, UserRepository userRepository, EmailService emailService) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // Borrow Book
    public ResponseEntity<BaseResponse<Borrow>> borrowBook(Long userId, Long bookId) {

        log.info("---borrowBook() started---");
        log.info("Attempting to borrow book. userId={}, bookId={}", userId, bookId);

        try {
            UserEntity user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                log.error("User not found with id={}", userId);
                return new ResponseEntity<>(new BaseResponse<>(HttpStatus.NOT_FOUND.value(), "User not found", null),HttpStatus.NOT_FOUND);
            }

            BookEntity book = bookRepository.findById(bookId).orElse(null);
            if (book == null) {
                log.error("Book not found with id={}", bookId);
                return new ResponseEntity<>(new BaseResponse<>(HttpStatus.NOT_FOUND.value(), "Book not found", null),HttpStatus.NOT_FOUND);
            }

            if (book.getStock() <= 0) {
                log.warn("Book out of stock. bookId={}", bookId);
                return new ResponseEntity<>(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "Book out of stock", null),HttpStatus.BAD_REQUEST);
            }

            book.setStock(book.getStock() - 1);
            bookRepository.save(book);

            BorrowEntity borrow = new BorrowEntity();
            borrow.setUser(user);
            borrow.setBook(book);
            borrow.setBorrowDate(LocalDateTime.now());

            BorrowEntity savedBorrow = borrowRepository.save(borrow);

            Borrow borrowDto = new Borrow(savedBorrow);

            emailService.sendEmail(
                user.getEmail(),
                "Book Borrowed Successfully",
                "Hello " + user.getUsername() + ",\n\nYou have successfully borrowed: " + book.getTitle()
            );

            log.info("Book borrowed successfully. borrowId={}", savedBorrow.getId());
            log.info("---borrowBook() ended---");

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.CREATED.value(), "Book borrowed successfully", borrowDto),HttpStatus.CREATED);

        }
        catch (Exception e) {

            log.error("Error while borrowing book", e);

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to borrow book", null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Return Book
    @Transactional
    public ResponseEntity<BaseResponse<Borrow>> returnBook(Long borrowId) {

        log.info("---returnBook() started---");
        log.info("Attempting to return book. borrowId={}", borrowId);

        try {
            BorrowEntity borrow = borrowRepository.findById(borrowId).orElse(null);

            if (borrow == null) {
                log.error("Borrow record not found. borrowId={}", borrowId);
                return new ResponseEntity<>(new BaseResponse<>(HttpStatus.NOT_FOUND.value(), "Borrow record not found", null),HttpStatus.NOT_FOUND);
            }

            if (borrow.getReturnDate() != null) {
                log.warn("Book already returned. borrowId={}", borrowId);
                return new ResponseEntity<>(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "Book already returned", null),HttpStatus.BAD_REQUEST);
            }

            borrow.setReturnDate(LocalDateTime.now());

            BookEntity book = borrow.getBook();
            book.setStock(book.getStock() + 1);
            bookRepository.save(book);

            BorrowEntity updatedBorrow = borrowRepository.save(borrow);

            Borrow borrowDto = new Borrow(updatedBorrow);

            log.info("Book returned successfully. borrowId={}", borrowId);
            log.info("---returnBook() ended---");

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.OK.value(), "Book returned successfully", borrowDto),HttpStatus.OK);

        }
        catch (Exception e) {
            log.error("Error while returning book", e);

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to return book", null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}