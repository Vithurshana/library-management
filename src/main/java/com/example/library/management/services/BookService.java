package com.example.library.management.services;

import com.example.library.management.entities.BookEntity;
import com.example.library.management.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;

    // Constructor Injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Get all books
    public List<BookEntity> getAllBooks() {
        log.info("Fetching all books");
        return bookRepository.findAll();
    }

    // Add new book
    public BookEntity addBook(BookEntity book) {
        log.info("Adding new book with title: {}", book.getTitle());
        return bookRepository.save(book);
    }

    // Get book by ID
    public BookEntity getBookById(Long id) {
        log.info("Fetching book with id: {}", id);
        return bookRepository.findById(id).orElseThrow(() -> {
            log.error("Book not found with id: {}", id);
            return new RuntimeException("Book not found");
        });
    }
}