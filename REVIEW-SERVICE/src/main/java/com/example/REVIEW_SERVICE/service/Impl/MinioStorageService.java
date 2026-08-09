package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.exception.StorageException;
import com.example.REVIEW_SERVICE.service.StorageService;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import io.minio.http.Method;

import java.util.concurrent.TimeUnit;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.review-attachments}")
    private String bucketName;

    @Value("${minio.presigned-url-expiry-minutes:15}")
    private int presignedUrlExpiryMinutes;

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

    @Override
    public InputStream download(
            String objectKey
    ) {

        try {

            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .build()
            );

        } catch (Exception exception) {

            throw new StorageException(
                    "Failed to download file from MinIO",
                    exception
            );
        }
    }

    @Override
    public boolean exists(
            String objectKey
    ) {

        try {

            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .build()
            );

            return true;

        } catch (
                ErrorResponseException exception
        ) {
            if (
                    exception.errorResponse()
                            .code()
                            .equals("NoSuchKey")
            ) {
                return false;
            }

            throw new StorageException(
                    "Failed to check whether object exists",
                    exception
            );

        } catch (Exception exception) {
            throw new StorageException(
                    "Failed to check whether object exists",
                    exception
            );
        }
    }

    @Override
    public String generatePresignedUrl(
            String objectKey
    ) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Http.Method.GET)
                            .bucket(bucketName)
                            .object(objectKey)
                            .expiry(
                                    presignedUrlExpiryMinutes,
                                    TimeUnit.MINUTES
                            )
                            .build()
            );

        } catch (Exception exception) {

            throw new StorageException(
                    "Failed to generate presigned URL",
                    exception
            );
        }
    }
}