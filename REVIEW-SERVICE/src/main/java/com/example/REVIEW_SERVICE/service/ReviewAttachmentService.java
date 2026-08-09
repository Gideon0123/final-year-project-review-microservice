package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.AttachmentDownload;
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
            Long attachmentId, Long reviewId
    );

    boolean attachmentExists(
            Long reviewId,
            Long attachmentId
    );

    AttachmentDownload downloadAttachment(
            Long attachmentId,
            Long reviewId
    );
}