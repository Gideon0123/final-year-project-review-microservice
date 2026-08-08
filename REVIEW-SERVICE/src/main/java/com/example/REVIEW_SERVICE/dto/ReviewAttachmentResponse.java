package com.example.REVIEW_SERVICE.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class ReviewAttachmentResponse {

    private UUID id;

    private Long reviewId;

    private String originalFilename;

    private String contentType;

    private Long fileSize;

    private LocalDateTime createdAt;
}