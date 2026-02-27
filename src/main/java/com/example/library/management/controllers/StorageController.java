package com.example.library.management.controllers;

import com.example.library.management.dtos.responses.BaseResponse;
import com.example.library.management.services.BookFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StorageController {

    private final BookFileService bookFileService;

    public StorageController(BookFileService bookFileService) {
        this.bookFileService = bookFileService;
    }

    @GetMapping("/files/{key}")
    public ResponseEntity<BaseResponse<String>> getFile(@PathVariable String key) {
        return bookFileService.getFile(key);
    }

//    @PostMapping("/files")
//    public ResponseEntity<BaseResponse<String>> getFile(@RequestBody String key) {
//        return bookFileService.getFile(key);
//    }
}