package com.example.library.management.helpers.upload;

import java.util.Base64;

public class FileUtils {

    public static String base64Decode(String encoded) {
        return new String(Base64.getDecoder().decode(encoded));
    }

    public static String base64Encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}