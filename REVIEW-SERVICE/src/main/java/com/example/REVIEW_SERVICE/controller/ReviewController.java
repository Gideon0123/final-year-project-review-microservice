package com.example.REVIEW_SERVICE.controller;

import com.example.REVIEW_SERVICE.dto.*;
import com.example.REVIEW_SERVICE.payload.PagedResponse;
import com.example.REVIEW_SERVICE.service.CurrentUserService;
import com.example.REVIEW_SERVICE.service.ReviewService;
import com.example.REVIEW_SERVICE.service.ReviewStatisticsService;
import com.example.REVIEW_SERVICE.utils.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Validated
public class ReviewController {

    private final CurrentUserService currentUserService;
    private final ReviewStatisticsService statisticsService;
    private final ReviewService reviewService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<ReviewResponse>> assignReviewer(
            @Valid @RequestBody AssignReviewerRequest request,
            HttpServletRequest httpRequest
    ) {
        ReviewResponse response = reviewService.assignReviewer(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<ReviewResponse>builder()
                                .success(true)
                                .message("Reviewer assigned successfully.")
                                .status(HttpStatus.CREATED.value())
                                .data(response)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @PreAuthorize("hasRole('REVIEWER')")
    @PatchMapping("/{reviewId}/accept")
    public ResponseEntity<ApiResponse<ReviewResponse>> acceptReviewer(
            @PathVariable long reviewId,
            HttpServletRequest httpRequest
    ) {
        ReviewResponse response = reviewService.acceptInvitation(reviewId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<ReviewResponse>builder()
                                .success(true)
                                .message("Review Accepted.")
                                .status(HttpStatus.OK.value())
                                .data(response)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @PreAuthorize("hasRole('REVIEWER')")
    @PatchMapping("/{reviewId}/decline")
    public ResponseEntity<ApiResponse<ReviewResponse>> declineReview(
            @PathVariable long reviewId,
            @Valid @RequestBody DeclineReviewRequest request,
            HttpServletRequest httpRequest
    ) {
        ReviewResponse response = reviewService.declineInvitation(reviewId, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<ReviewResponse>builder()
                                .success(true)
                                .message("Review Declined.")
                                .status(HttpStatus.OK.value())
                                .data(response)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @PreAuthorize("hasRole('REVIEWER')")
    @PostMapping("/{reviewId}/submit")
    public ResponseEntity<ApiResponse<ReviewResponse>> submitReview(
            @PathVariable long reviewId,
            @Valid @RequestBody SubmitReviewRequest request,
            HttpServletRequest httpRequest
    ) {
        ReviewResponse response = reviewService.submitReview(reviewId, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<ReviewResponse>builder()
                                .success(true)
                                .message("Review Submitted.")
                                .status(HttpStatus.OK.value())
                                .data(response)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{reviewId}/decision")
    public ResponseEntity<ApiResponse<ReviewResponse>> editorialDecision(
            @PathVariable long reviewId,
            @Valid @RequestBody EditorialDecisionRequest request,
            HttpServletRequest httpRequest
    ) {
        ReviewResponse response = reviewService.editorDecision(reviewId, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<ReviewResponse>builder()
                                .success(true)
                                .message("Editorial Decision.")
                                .status(HttpStatus.OK.value())
                                .data(response)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @PreAuthorize("hasRole('REVIEWER')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<PagedResponse<ReviewSummaryResponse>>> getAllPapers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            HttpServletRequest request
    ) {
        int adjustedPage = Math.max(page - 1, 0);
        PagedResponse<ReviewSummaryResponse> papers = reviewService.getAssignedReviews(
                adjustedPage,
                size,
                sortBy,
                sortDirection
        );

        PagedResponse<ReviewSummaryResponse> response =
                PagedResponse.<ReviewSummaryResponse>builder()
                        .content(papers.getContent())
                        .size(papers.getSize())
                        .page(papers.getPage())
                        .first(papers.isFirst())
                        .last(papers.isLast())
                        .totalElements(papers.getTotalElements())
                        .totalPages(papers.getTotalPages())
                        .build();

        return ResponseEntity.ok(
                ApiResponse.<PagedResponse<ReviewSummaryResponse>>builder()
                        .success(true)
                        .message("Reviews fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paper/{paperId}")
    public ResponseEntity<ApiResponse<PagedResponse<ReviewSummaryResponse>>> getPaperReviews(
            @PathVariable long paperId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            HttpServletRequest request
    ) {
        int adjustedPage = Math.max(page - 1, 0);
        PagedResponse<ReviewSummaryResponse> papers = reviewService.getPaperReviews(
                paperId,
                adjustedPage,
                size,
                sortBy,
                sortDirection
        );

        PagedResponse<ReviewSummaryResponse> response =
                PagedResponse.<ReviewSummaryResponse>builder()
                        .content(papers.getContent())
                        .size(papers.getSize())
                        .page(papers.getPage())
                        .first(papers.isFirst())
                        .last(papers.isLast())
                        .totalElements(papers.getTotalElements())
                        .totalPages(papers.getTotalPages())
                        .build();

        return ResponseEntity.ok(
                ApiResponse.<PagedResponse<ReviewSummaryResponse>>builder()
                        .success(true)
                        .message("Reviews fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReview(
            @PathVariable long reviewId,
            HttpServletRequest httpRequest
    ) {
        ReviewResponse response = reviewService.getReview(reviewId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<ReviewResponse>builder()
                                .success(true)
                                .message("Review Fetched.")
                                .status(HttpStatus.OK.value())
                                .data(response)
                                .path(httpRequest.getRequestURI())
                                .traceId(TraceIdUtil.generate())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @GetMapping("/me/statistics")
    public ResponseEntity<ApiResponse<ReviewStatisticsResponse>> getStatistics(
            HttpServletRequest request
    ) {
        Long reviewerId = currentUserService.getCurrentUser().getId();

        ReviewStatisticsResponse response = statisticsService.getReviewerStatistics(
                reviewerId
        );

        return ResponseEntity.ok(
                ApiResponse.<ReviewStatisticsResponse>builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("Statistics retrieved successfully")
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
