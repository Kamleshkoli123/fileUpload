package com.DocMate.controller;

import com.DocMate.service.JwtService;
import com.DocMate.util.FileValidationUtil;
import com.DocMate.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileUploadService fileUploadService;
    
    @Autowired
    private JwtService jwtService;

    @PostMapping("/doc")
    public ResponseEntity<String> uploadDocument(@RequestHeader("Authorization") String jwtToken,
                                                 @RequestParam String documentName,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("serviceName") String serviceName) {
    	String contact = null;
        try {
        	
            jwtService.validateToken(jwtToken);  // Validate JWT
            // Extract contact from JWT
            contact = jwtService.extractContactFromJwt(jwtToken);
            logger.info("Upload document request received for contact: {}", contact);

            // Validate file format
            FileValidationUtil.validateFile(file);

            // Save file to directory
            fileUploadService.saveFile(contact, documentName, serviceName, file);
            logger.info("File uploaded successfully for contact: {}", contact);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            logger.error("Error uploading document for contact: {}", contact, e);
            return ResponseEntity.status(500).body("File upload failed.");
        }
    }
}
 