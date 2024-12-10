package com.DocMate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileValidationUtil.class);

    // Max file size: 5 MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    // Validates the file's size and type
    public static void validateFile(MultipartFile file) throws IllegalArgumentException {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds limit of 5MB");
        }

        if (getFileExtension(file) == null) {
            throw new IllegalArgumentException("Unsupported file type: " + file.getContentType());
        }
        
        if (!file.getContentType().equals("application/pdf") && !file.getContentType().equals("image/jpeg")) {
            logger.warn("Invalid file format for upload.");
            throw new IllegalArgumentException("Unsupported file type:");
        }
    }

    // Determines the file extension based on content type
    public static String getFileExtension(MultipartFile file) {
        String contentType = file.getContentType();
        logger.info("File content type: {}", contentType);
        switch (contentType) {
            case "application/pdf":
                return ".pdf";
            case "image/jpeg":
                return ".jpg";
            case "image/png":
                return ".png";
            default:
                logger.warn("Unsupported content type: {}", contentType);
                return null;
        }
    }
}