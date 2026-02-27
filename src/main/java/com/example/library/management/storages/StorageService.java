package com.example.library.management.storages;

import org.springframework.stereotype.Service;
import java.nio.file.*;

@Service
public class StorageService {

    private final Path rootLocation = Paths.get("Uploads");

    public String getFile(String key) {
        try {
            Path filePath = rootLocation.resolve(key).normalize();
            return filePath.toString();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to load file", e);
        }
    }
}