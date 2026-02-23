package com.example.library.management.controllers;

import com.example.library.management.entities.BookEntity;
import com.example.library.management.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET all books
    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }

    // GET book by id
    @GetMapping("/{id}")
    public BookEntity getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // POST add new book
    @PostMapping
    public BookEntity addBook(@RequestBody BookEntity book) {
        return bookService.addBook(book);
    }
}

