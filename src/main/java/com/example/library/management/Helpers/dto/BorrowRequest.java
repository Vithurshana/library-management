package com.example.library.management.Helpers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BorrowRequest {
    private Long userId;
    private Long bookId;
}