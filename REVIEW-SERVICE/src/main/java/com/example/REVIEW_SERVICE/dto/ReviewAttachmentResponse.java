package com.example.REVIEW_SERVICE.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewAttachmentResponse {

    private Long id;

    private Long reviewId;

    private String originalFilename;

    private String contentType;

    private Long fileSize;

    private LocalDateTime createdAt;
}