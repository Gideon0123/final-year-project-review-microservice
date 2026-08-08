package com.example.REVIEW_SERVICE.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioBucketConfig {

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioBucketConfig(
            MinioClient minioClient,
            @Value("${minio.bucket.review-attachments}")
            String bucketName
    ) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    @PostConstruct
    public void initializeBucket() {

        try {

            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );

            if (!exists) {

                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );

            }

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Failed to initialize MinIO bucket: "
                            + bucketName,
                    exception
            );
        }
    }
}