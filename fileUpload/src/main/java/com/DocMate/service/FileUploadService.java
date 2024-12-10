package com.DocMate.service;

import com.DocMate.util.FileValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadService {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    private static final String ROOT_DIRECTORY = "D:/Android-project/cyberCity/uploads/";

    public void saveFile(String contact, String documentName, String serviceName, MultipartFile file) throws Exception {
        String directoryPath = ROOT_DIRECTORY + contact;
        File directory = new File(directoryPath);

        try {
            // Validate file size and type
            FileValidationUtil.validateFile(file);

            // Create directory if it doesn't exist
            if (!directory.exists() && !directory.mkdirs()) {
                logger.error("Failed to create directory at: {}", directoryPath);
                throw new IOException("Could not create directory.");
            }

            // Full file path: service-name_document-name_contact.extension
            String fileName = serviceName + "_" + documentName + "_" + contact + FileValidationUtil.getFileExtension(file);
            Path destinationPath = Paths.get(directoryPath, fileName);

            // Save the file
            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                logger.info("File saved successfully at: {}", destinationPath);
            } catch (IOException e) {
                logger.error("Failed to save file at: {}", destinationPath, e);
                throw new IOException("Error saving file: " + e.getMessage(), e);
            }

        } catch (Exception e) {
            logger.error("Error during file upload for contact {}: {}", contact, e.getMessage(), e);
            throw e;
        }
    }
}