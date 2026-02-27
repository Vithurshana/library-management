package com.example.library.management.dtos.requests.books;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BorrowBookRequest {
    private Long userId;
    private Long bookId;
}