package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.*;
import com.example.REVIEW_SERVICE.entity.CurrentUser;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import com.example.REVIEW_SERVICE.mapper.ReviewMapper;
import com.example.REVIEW_SERVICE.payload.PagedResponse;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CurrentUserService currentUserService;
    private final BlindReviewService blindReviewService;
    private final ReviewLookupService lookupService;
    private final ReviewValidationService validationService;
    private final ReviewAuthorizationService authorizationService;
    private final ReviewDeadlineService deadlineService;
    private final ReviewStatisticsService statisticsService;
    private final ResearchPaperLookupService paperLookupService;

    private ReviewSummaryResponse toSummary(
            Review review
    ) {
        return ReviewSummaryResponse.builder()
                .id(review.getId())
                .paperId(review.getPaperId())
                .reviewerId(review.getReviewerId())
                .status(review.getStatus())
                .deadline(review.getDeadline())
                .revisionNumber(review.getRevisionNumber())
//                .recommendation(review.getRecommendation())
                .build();

    }

    private Pageable buildPageable(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        return PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.fromString(sortDirection),
                        sortBy
                )
        );
    }

    @Override
    public ReviewResponse assignReviewer(
            AssignReviewerRequest request
    ) {
        validationService.validateAssignment(
                request.getPaperId(),
                request.getReviewerId()
        );

        paperLookupService.getPaperSummary(request.getPaperId());
        Review review = Review.builder()
                .paperId(request.getPaperId())
                .reviewerId(request.getReviewerId())
                .status(ReviewStatus.PENDING_INVITATION)
                .deadline(deadlineService.calculateDeadline())
                .reviewRound(1)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);

        return reviewMapper.toResponse(review);
    }

    @Override
    public ReviewResponse acceptInvitation(
            Long reviewId
    ) {
        Review review = lookupService.getReviewById(reviewId);
        authorizationService.verifyReviewer(review);

        review.setStatus(ReviewStatus.INVITATION_ACCEPTED);
        review.setAcceptedAt(LocalDateTime.now());
        reviewRepository.save(review);

        return reviewMapper.toResponse(review);
    }

    @Override
    public ReviewResponse declineInvitation(
            Long reviewId,
            DeclineReviewRequest request
    ) {
        Review review = lookupService.getReviewById(reviewId);

        authorizationService.verifyReviewer(review);
        review.setStatus(ReviewStatus.INVITATION_DECLINED);
        review.setDeclineReason(request.getReason());
        review.setDeclinedAt(LocalDateTime.now());

        reviewRepository.save(review);
        return reviewMapper.toResponse(review);
    }

    @Override
    public ReviewResponse submitReview(
            Long reviewId,
            SubmitReviewRequest request
    ) {
        Review review = lookupService.getReviewById(reviewId);

        authorizationService.verifyReviewer(review);
        validationService.validateSubmission(review);
        review.setCommentsForAuthor(request.getCommentsForAuthor());
        review.setCommentsForEditor(request.getCommentsForEditor());
        review.setOverallScore(request.getOverallScore());
        review.setRecommendation(request.getRecommendation());
        review.setStatus(ReviewStatus.SUBMITTED);
        review.setSubmittedAt(LocalDateTime.now());

        reviewRepository.save(review);

        return reviewMapper.toResponse(review);
    }

    @Override
    public ReviewResponse editorDecision(
            Long reviewId,
            EditorialDecisionRequest request
    ) {
        authorizationService.verifyEditor();
        Review review = lookupService.getReviewById(reviewId);
        validationService.validateDecision(review);

        review.setDecision(request.getDecision());
        review.setCommentsForEditor(request.getComment());
        review.setDecisionAt(LocalDateTime.now());

        reviewRepository.save(review);

        return reviewMapper.toResponse(review);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ReviewSummaryResponse> getAssignedReviews(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Pageable pageable = buildPageable(
                page,
                size,
                sortBy,
                sortDirection
        );

        Page<Review> reviews = lookupService.getReviewsForReviewer(
                currentUserService.getCurrentUser().getId(),
                pageable
        );

        Page<ReviewSummaryResponse> content = reviews.map(this::toSummary);
        return new PagedResponse<>(content);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ReviewSummaryResponse> getPaperReviews(
            Long paperId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        authorizationService.verifyEditor();

        Pageable pageable = buildPageable(
                page,
                size,
                sortBy,
                sortDirection
        );

        Page<Review> reviews = lookupService.getReviewsForPaper(
                paperId,
                pageable
        );

        Page<ReviewSummaryResponse> content = reviews.map(this::toSummary);

        return new PagedResponse<>(content);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponse getReview(
            Long reviewId
    ) {
        Review review = lookupService.getReviewById(reviewId);
        CurrentUser user = currentUserService.getCurrentUser();

        if (user.getRole().equals("AUTHOR")) {
            return blindReviewService.maskForAuthor(review);
        }

        if (user.getRole().equals("REVIEWER")) {
            authorizationService.verifyReviewer(
                    review
            );

            return blindReviewService.maskForReviewer(
                    review
            );
        }

        return reviewMapper.toResponse(review);
    }
}