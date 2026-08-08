package com.example.REVIEW_SERVICE.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void upload(
            String objectKey,
            MultipartFile file
    );
}