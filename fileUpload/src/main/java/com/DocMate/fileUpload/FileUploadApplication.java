package com.DocMate.fileUpload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.DocMate.service.JwtService;

@SpringBootApplication(scanBasePackages = "com.DocMate")
public class FileUploadApplication {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadApplication.class);
	
	public static void main(String[] args) {
		logger.info("starting file upload application..");
		SpringApplication.run(FileUploadApplication.class, args);
		logger.info("file upload application started successfully..");
	}

}
