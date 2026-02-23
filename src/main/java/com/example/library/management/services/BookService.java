package com.example.library.management.services;

import com.example.library.management.entities.BookEntity;
import com.example.library.management.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    // Constructor Injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Get all books
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    // Add new book
    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book);
    }

    // Get book by ID
    public BookEntity getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}