package com.example.REVIEW_SERVICE.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {

    void upload(String objectKey, MultipartFile file);
    InputStream download(String objectKey);
    boolean exists(String objectKey);
    String generatePresignedUrl(String objectKey);
}