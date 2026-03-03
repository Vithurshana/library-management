package com.example.library.management.services;

import com.example.library.management.dtos.books.Book;
import com.example.library.management.dtos.responses.BaseResponse;
import com.example.library.management.dtos.responses.books.BookListResponse;
import com.example.library.management.entities.BookEntity;
import com.example.library.management.repositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Get all books
    public ResponseEntity<BaseResponse<BookListResponse>> getAllBooks() {
        log.info("---getAllBooks() started---");
        try {
            List<Book> books = bookRepository.findAll().stream().map(e -> new Book(e)).toList();
            log.info("Successfully fetched {} books", books.size());
            BookListResponse bookListResponse = new BookListResponse(books);

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.OK.value(), "Books fetched successfully", bookListResponse),HttpStatus.OK);

        }
        catch (Exception e) {
            log.error("Error while fetching books", e);

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to fetch books", null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add new book
//    public ResponseEntity<BaseResponse<Book>> addBook(BookEntity book) {
//
//        log.info("---addBook() started---");
//        log.info("Adding new book with title: {}", book.getTitle());
//        ResponseEntity<BaseResponse<Book>> response;
//
//        try {
//            Book savedBook = new Book(bookRepository.save(book));
//
//            log.info("Book added successfully with id: {}", savedBook.getId());
//            response =
//                    new ResponseEntity<>(new BaseResponse<>(HttpStatus.CREATED.value(), "Book added successfully",
//                            savedBook), HttpStatus.CREATED);
//
//
//        }
//        catch (Exception e) {
//
//            log.error("Error while adding book: {}", book.getTitle(), e);
//            response =
//                    new ResponseEntity<>( new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add book", null),
//                            HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//
//        log.info("---addBook() started---");
//        return response;
//    }
//    public ResponseEntity<BaseResponse<Book>> addBook(BookEntity book, MultipartFile file) {
    public ResponseEntity<BaseResponse<Book>> addBook(
            String title,
            String author,
            int stock,
            MultipartFile file) {
        log.info("---addBook() started---");
//        log.info("Adding new book with title: {}", book.getTitle());
        BookEntity book = new BookEntity();
        book.setTitle(title);
        book.setAuthor(author);
        book.setStock(stock);

        try {

            // Save file to Uploads folder
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path uploadPath = Paths.get("Uploads");
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save file path in entity
            book.setPdfPath(fileName);

            // Save book in DB
            BookEntity savedEntity = bookRepository.save(book);

            Book savedBook = new Book(savedEntity);

            log.info("Book added successfully with id: {}", savedBook.getId());

            return new ResponseEntity<>(
                    new BaseResponse<>(HttpStatus.CREATED.value(),
                            "Book added successfully",
                            savedBook),
                    HttpStatus.CREATED
            );

        }
        catch (Exception e) {

            log.error("Error while adding book: {}", book.getTitle(), e);

            return new ResponseEntity<>(
                    new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to add book",
                            null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ResponseEntity<BaseResponse<Book>> getBookById(Long id) {

        log.info("---getBookById() started---");

        try {
            BookEntity entity = bookRepository.findById(id).orElse(null);

            if (entity == null) {
                log.error("Book not found with id: {}", id);

                return new ResponseEntity<>(new BaseResponse<>(HttpStatus.NOT_FOUND.value(), "Book not found with id " + id, null),HttpStatus.NOT_FOUND);
            }

            Book bookDto = new Book(entity);

            log.info("Book found with id: {}", id);

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.OK.value(), "Book fetched successfully", bookDto),HttpStatus.OK);

        }
        catch (Exception e) {
            log.error("Database error while fetching book with id: {}", id, e);

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong while fetching book", null),HttpStatus.INTERNAL_SERVER_ERROR);

        }
        finally {
            log.info("---getBookById() ended---");
        }
    }
}