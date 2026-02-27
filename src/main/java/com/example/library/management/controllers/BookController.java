package com.example.library.management.controllers;

import com.example.library.management.dtos.books.Book;
import com.example.library.management.dtos.responses.BaseResponse;
import com.example.library.management.dtos.responses.books.BookListResponse;
import com.example.library.management.entities.BookEntity;
import com.example.library.management.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET all books
    @GetMapping
    public ResponseEntity<BaseResponse<BookListResponse>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Book>> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // POST add new book
    @PostMapping
    public ResponseEntity<BaseResponse<Book>> addBook(@RequestBody BookEntity book) {
        return  bookService.addBook(book);
    }
}

