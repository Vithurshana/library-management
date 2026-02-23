package com.example.library.management.controllers;

import com.example.library.management.Helpers.dto.BorrowRequest;
import com.example.library.management.Helpers.dto.ReturnRequest;
import com.example.library.management.entities.BorrowEntity;
import com.example.library.management.services.BorrowService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    // Borrow book
    @PostMapping("/borrow")
    public BorrowEntity borrowBook(@RequestBody BorrowRequest request) {
        return borrowService.borrowBook(request.getUserId(), request.getBookId());
    }

    // Return book
    @PostMapping("/return")
    public BorrowEntity returnBook(@RequestBody ReturnRequest request) {
        return borrowService.returnBook(request.getBorrowId());
    }
}