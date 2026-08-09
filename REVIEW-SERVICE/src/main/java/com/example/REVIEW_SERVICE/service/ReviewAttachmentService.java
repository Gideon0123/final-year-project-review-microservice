package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.ReviewAttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public interface ReviewAttachmentService {

    ReviewAttachmentResponse uploadAttachment(
            Long reviewId,
            MultipartFile file
    );

    ReviewAttachmentResponse getAttachmentMetadata(
            Long attachmentId
    );

    boolean attachmentExists(
            Long attachmentId
    );

    InputStream downloadAttachment(
            Long attachmentId
    );
}