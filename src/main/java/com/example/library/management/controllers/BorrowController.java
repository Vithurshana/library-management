package com.example.library.management.controllers;

import com.example.library.management.dtos.books.Borrow;
import com.example.library.management.dtos.requests.books.BorrowBookRequest;
import com.example.library.management.dtos.requests.books.ReturnBookRequest;
import com.example.library.management.entities.BorrowEntity;
import com.example.library.management.services.BorrowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.library.management.dtos.responses.BaseResponse;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    // Borrow book
    @PostMapping("/borrow")
    public ResponseEntity<BaseResponse<Borrow>> borrowBook(@RequestBody BorrowBookRequest request) {
        return borrowService.borrowBook(request.getUserId(), request.getBookId());
    }

    // Return book
    @PostMapping("/return")
    public ResponseEntity<BaseResponse<Borrow>> returnBook(@RequestBody ReturnBookRequest request) {
        return borrowService.returnBook(request.getBorrowId());
    }
}