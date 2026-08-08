package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.exception.StorageException;
import com.example.REVIEW_SERVICE.service.StorageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.review-attachments}")
    private String bucketName;

    @Override
    public void upload(
            String objectKey,
            MultipartFile file
    ) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .stream(
                                    file.getInputStream(),
                                    file.getSize(),
                                    -1L
                            )
                            .contentType(
                                    file.getContentType() != null
                                            ? file.getContentType()
                                            : "application/octet-stream"
                            )
                            .build()
            );

        } catch (Exception exception) {

            throw new StorageException(
                    "Failed to upload file to MinIO",
                    exception
            );
        }
    }
}