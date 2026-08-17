package com.example.REVIEW_SERVICE.controller;

import com.example.REVIEW_SERVICE.dto.ApiResponse;
import com.example.REVIEW_SERVICE.dto.AttachmentDownload;
import com.example.REVIEW_SERVICE.dto.ReviewAttachmentResponse;
import com.example.REVIEW_SERVICE.service.ReviewAttachmentService;
import com.example.REVIEW_SERVICE.utils.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewAttachmentController {

    private final ReviewAttachmentService reviewAttachmentService;

    @PostMapping(
            value = "/{reviewId}/attachments",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<ReviewAttachmentResponse>> uploadAttachment(
            @PathVariable Long reviewId,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest httpRequest
    ) {
        ReviewAttachmentResponse response =
                reviewAttachmentService.uploadAttachment(
                        reviewId,
                        file
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<ReviewAttachmentResponse>builder()
                                .success(true)
                                .message("Review Attachment Uploaded successfully successfully.")
                                .status(HttpStatus.CREATED.value())
                                .data(response)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @GetMapping("/{reviewId}/attachments/{attachmentId}")
    public ResponseEntity<ApiResponse<ReviewAttachmentResponse>> getAttachmentMetadata(
            @PathVariable Long reviewId,
            @PathVariable Long attachmentId,
            HttpServletRequest httpRequest
    ) {
        ReviewAttachmentResponse response =
                reviewAttachmentService.getAttachmentMetadata(
                                attachmentId, reviewId
                        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<ReviewAttachmentResponse>builder()
                                .success(true)
                                .message("Review Attachment metaData Fetched successfully.")
                                .status(HttpStatus.OK.value())
                                .data(response)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @GetMapping("/{reviewId}/attachments/{attachmentId}/download")
    public ResponseEntity<ApiResponse<Resource>> downloadAttachment(
            @PathVariable Long reviewId,
            @PathVariable Long attachmentId,
            HttpServletRequest httpRequest
    ) {

        AttachmentDownload download =
                reviewAttachmentService.downloadAttachment(
                        reviewId,
                        attachmentId
                );

        InputStreamResource resource = new InputStreamResource(
                download.getInputStream()
        );

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(download.getContentType())
                )
                .contentLength(
                        download.getFileSize()
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + download.getFilename()
                                + "\""
                )
                .body(
                        ApiResponse.<Resource>builder()
                                .success(true)
                                .message("Review Attachment Downloaded successfully.")
                                .status(HttpStatus.OK.value())
                                .data(resource)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @GetMapping("/{reviewId}/attachments/{attachmentId}/exists")
    public ResponseEntity<ApiResponse<Boolean>> attachmentExists(
            @PathVariable Long reviewId,
            @PathVariable Long attachmentId,
            HttpServletRequest httpRequest
    ) {
        boolean exists = reviewAttachmentService.attachmentExists(
                        reviewId,
                        attachmentId
                );

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<Boolean>builder()
                                .success(true)
                                .message("Review Attachment Exists")
                                .status(HttpStatus.OK.value())
                                .data(exists)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @GetMapping(
            "/{reviewId}/attachments/{attachmentId}/url"
    )
    public ResponseEntity<ApiResponse<String>> generateAttachmentUrl(
            @PathVariable Long reviewId,
            @PathVariable Long attachmentId,
            HttpServletRequest httpRequest
    ) {
        String url = reviewAttachmentService.generateAttachmentUrl(
                reviewId,
                attachmentId
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<String >builder()
                                .success(true)
                                .message("Document URL")
                                .status(HttpStatus.OK.value())
                                .data(url)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable Long attachmentId
    ) {

        reviewAttachmentService.deleteAttachment(
                attachmentId
        );

        return ResponseEntity.noContent().build();
    }
}