package com.example.library.management.controllers;

import com.example.library.management.dtos.books.Book;
import com.example.library.management.dtos.responses.BaseResponse;
import com.example.library.management.dtos.responses.books.BookListResponse;
import com.example.library.management.entities.BookEntity;
import com.example.library.management.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
//    @PostMapping
//    public ResponseEntity<BaseResponse<Book>> addBook(@RequestBody BookEntity book) {
//        return  bookService.addBook(book);
//    }
//    @PostMapping("/books")
//    public ResponseEntity<BaseResponse<Book>> addBook(@RequestPart("book") BookEntity book, @RequestPart("file") MultipartFile file) {
//        return bookService.addBook(book, file);
//    }
    @PostMapping(value = "/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<Book>> addBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("stock") int stock,
            @RequestParam("file") MultipartFile file) {

        return bookService.addBook(title, author, stock, file);
    }
}

