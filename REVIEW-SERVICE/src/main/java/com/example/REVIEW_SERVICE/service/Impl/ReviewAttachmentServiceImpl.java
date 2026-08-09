package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.AttachmentDownload;
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

import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewAttachmentServiceImpl implements ReviewAttachmentService {

    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;
    private static final Set<String> ALLOWED_CONTENT_TYPES =
            Set.of(
                    "application/pdf",
                    "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.ms-excel",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "image/jpeg",
                    "image/png"
            );

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
                .orElseThrow(() -> new ResourceNotFoundException(
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

        String objectKey = generateObjectKey(
                reviewId,
                originalFilename
        );

        storageService.upload(objectKey, file);

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

        ReviewAttachment saved = reviewAttachmentRepository.save(attachment);

        return reviewAttachmentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewAttachmentResponse getAttachmentMetadata(
            Long attachmentId, Long reviewId
    ) {
        ReviewAttachment attachment =
                reviewAttachmentRepository
                        .findByIdAndReviewId(
                                attachmentId,
                                reviewId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Attachment not found for this review"
                                )
                        );
        return reviewAttachmentMapper.toResponse(attachment);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean attachmentExists(
            Long attachmentId
    ) {
        ReviewAttachment attachment = reviewAttachmentRepository.findById(attachmentId)
                .orElse(null);

        if (attachment == null) {
            return false;
        }

        return storageService.exists(
                attachment.getObjectKey()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AttachmentDownload downloadAttachment(
            Long attachmentId
    ) {
        ReviewAttachment attachment =
                reviewAttachmentRepository.findById(attachmentId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Attachment not found with id: "
                                        + attachmentId
                                )
                        );

        if (!storageService.exists(attachment.getObjectKey())) {
            throw new ResourceNotFoundException(
                    "Attachment file not found in storage"
            );
        }

        InputStream inputStream = storageService.download(
                attachment.getObjectKey()
        );

        return AttachmentDownload.builder()
                .inputStream(inputStream)
                .filename(attachment.getOriginalFilename())
                .contentType(attachment.getContentType())
                .fileSize(attachment.getFileSize())
                .build();
    }

    private void validateFile(
            MultipartFile file
    ) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size must not exceed 20 MB");
        }

        String filename = file.getOriginalFilename();

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("File must have a valid filename");
        }

        String contentType = file.getContentType();

        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    "Unsupported file type: "
                            + contentType
            );
        }
    }

    private String generateObjectKey(
            Long reviewId,
            String originalFilename
    ) {
        String extension = "";
        int extensionIndex = originalFilename.lastIndexOf('.');

        if (
                extensionIndex > 0
                        && extensionIndex
                        < originalFilename.length() - 1
        ) {
            extension = originalFilename.substring(extensionIndex).toLowerCase();
        }

        return "reviews/"
                + reviewId
                + "/"
                + UUID.randomUUID()
                + extension;
    }
}