package com.example.library.management.controllers;

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
    public BorrowEntity borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return borrowService.borrowBook(userId, bookId);
    }

    // Return book
    @PostMapping("/return/{borrowId}")
    public BorrowEntity returnBook(@PathVariable Long borrowId) {
        return borrowService.returnBook(borrowId);
    }
}