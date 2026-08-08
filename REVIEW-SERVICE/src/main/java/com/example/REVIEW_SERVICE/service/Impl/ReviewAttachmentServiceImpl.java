package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.ReviewAttachmentResponse;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.entity.ReviewAttachment;
import com.example.REVIEW_SERVICE.exception.ResourceNotFoundException;
import com.example.REVIEW_SERVICE.mapper.ReviewAttachmentMapper;
import com.example.REVIEW_SERVICE.repository.ReviewAttachmentRepository;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.ReviewAttachmentService;
import com.example.REVIEW_SERVICE.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewAttachmentServiceImpl implements ReviewAttachmentService {

    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;
    private final ReviewRepository reviewRepository;
    private final ReviewAttachmentRepository reviewAttachmentRepository;
    private final StorageService storageService;
    private final ReviewAttachmentMapper reviewAttachmentMapper;

    @Value("${minio.bucket.review-attachments}")
    private String bucketName;

    @Override
    @Transactional
    public ReviewAttachmentResponse uploadAttachment(
            Long reviewId,
            MultipartFile file
    ) {

        validateFile(file);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Review not found with id: "
                                        + reviewId
                        )
                );

        String originalFilename =
                StringUtils.cleanPath(
                        file.getOriginalFilename() != null
                                ? file.getOriginalFilename()
                                : "unnamed-file"
                );

        String objectKey =  generateObjectKey(
                reviewId,
                originalFilename
        );

        storageService.upload(
                objectKey,
                file
        );

        ReviewAttachment attachment =
                ReviewAttachment.builder()
                        .review(review)
                        .originalFilename(originalFilename)
                        .objectKey(objectKey)
                        .contentType(
                                file.getContentType() != null
                                        ? file.getContentType()
                                        : "application/octet-stream"
                        )
                        .fileSize(file.getSize())
                        .bucketName(bucketName)
                        .build();

        ReviewAttachment saved =
                reviewAttachmentRepository.save(
                        attachment
                );

        return reviewAttachmentMapper.toResponse(
                saved
        );
    }

    private void validateFile(
            MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "File must not be empty"
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {

            throw new IllegalArgumentException(
                    "File size must not exceed 20 MB"
            );
        }

        String filename = file.getOriginalFilename();

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException(
                    "File must have a valid filename"
            );
        }
    }

    private String generateObjectKey(
            UUID reviewId,
            String originalFilename
    ) {
        String extension = "";
        int extensionIndex = originalFilename.lastIndexOf('.');

        if (extensionIndex > 0
                && extensionIndex
                < originalFilename.length() - 1
        ) {
            extension = originalFilename.substring(
                    extensionIndex).toLowerCase();
        }

        return "reviews/"
                + reviewId
                + "/"
                + UUID.randomUUID()
                + extension;
    }
}