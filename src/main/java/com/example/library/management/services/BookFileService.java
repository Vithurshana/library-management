package com.example.library.management.services;

import com.example.library.management.dtos.responses.BaseResponse;
import com.example.library.management.helpers.upload.FileUtils;
import com.example.library.management.storages.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookFileService {
    private final StorageService storageService;

    public BookFileService(StorageService storageService) {
        this.storageService = storageService;
    }

    public ResponseEntity<BaseResponse<String>> getFile(String key) {
        String methodName = "BookFileService-getFile";

        try {

            log.info(methodName + " start");

            // Decode base64 key
            key = FileUtils.base64Decode(key);

            System.out.println("decoded key = " + key);

            // Get absolute file path
            String fullPath = storageService.getFile(key);

            // Convert to relative path
            Path uploadsRoot = Paths.get("Uploads").toAbsolutePath().normalize();
            Path filePath = Paths.get(fullPath).toAbsolutePath().normalize();

            Path relativePath = uploadsRoot.relativize(filePath);

            String finalPath = "/Uploads/" +
                    relativePath.toString().replace("\\", "/");

            System.out.println("relativePath = " + finalPath);

            log.info(methodName + " end");

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.OK.value(), "path fetched successfully", finalPath),HttpStatus.OK);

        } catch (Exception ex) {

            ex.printStackTrace();
            log.info(methodName + " end");

            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to fetch path", null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}