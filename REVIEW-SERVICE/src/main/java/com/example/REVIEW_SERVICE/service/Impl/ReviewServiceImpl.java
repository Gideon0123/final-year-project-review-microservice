package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.*;
import com.example.REVIEW_SERVICE.dto.events.*;
import com.example.REVIEW_SERVICE.entity.CurrentUser;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.enums.EditorialDecision;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import com.example.REVIEW_SERVICE.exception.AccessDeniedException;
import com.example.REVIEW_SERVICE.exception.ReviewNotFoundException;
import com.example.REVIEW_SERVICE.mapper.ReviewMapper;
import com.example.REVIEW_SERVICE.payload.PagedResponse;
import com.example.REVIEW_SERVICE.publisher.ReviewEventPublisher;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
    private final ReviewRoundService reviewRoundService;
    private final ResearchPaperLookupService paperLookupService;
    private final ResearchStatusService researchStatusService;
    private final ReviewAttachmentService reviewAttachmentService;
    private final ReviewDecisionHistoryService decisionHistoryService;
    private final RevisionHistoryService revisionHistoryService;
    private final ReviewEventPublisher reviewEventPublisher;
    private final AuthLookupService authLookupService;

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

    private String getReviewerEmail(Long reviewerId) {
        return authLookupService.getReviewer(reviewerId).getEmail();
    }

    private String getAuthorEmail(Long paperId) {
        return paperLookupService.getPaperSummary(paperId).getAuthorEmail();
    }

    @Override
    public ReviewResponse assignReviewer(
            AssignReviewerRequest request
    ) {
        ReviewerSummaryResponse reviewer = authLookupService.getReviewer(
                request.getReviewerId()
        );

        validationService.validateAssignment(
                request.getPaperId(),
                reviewer
        );

        PaperSummaryResponse paper = paperLookupService.getPaperSummary(request.getPaperId());
        Review review = Review.builder()
                .paperId(request.getPaperId())
                .reviewerId(request.getReviewerId())
                .authorId(paper.getAuthorId())
                .status(ReviewStatus.PENDING_INVITATION)
                .deadline(deadlineService.calculateDeadline())
                .reviewRound(
                        reviewRoundService.getCurrentRound(
                                request.getPaperId()
                        )
                )
                .revisionNumber(paper.getRevisionNumber())
                .assignedBy(currentUserService.getCurrentUser().getId())
                .invitationSentAt(LocalDateTime.now())
                .assignedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);

        reviewEventPublisher.publishAssigned(
                ReviewAssignedEvent.builder()
                        .reviewId(review.getId())
                        .paperId(review.getPaperId())
                        .reviewerId(review.getReviewerId())
                        .reviewerEmail(getReviewerEmail(review.getReviewerId()))
                        .deadline(review.getDeadline())
                        .reviewRound(review.getReviewRound())
                        .revisionNumber(review.getRevisionNumber())
                        .build()
        );

        return reviewMapper.toResponse(review);
    }

    @Override
    public ReviewResponse acceptInvitation(
            Long reviewId
    ) {
        Review review = lookupService.getReviewById(reviewId);
        authorizationService.verifyReviewer(review);
        validationService.validateInvitationAcceptance(review);
//        ReviewerSummaryResponse reviewer = authLookupService.getReviewer(review.getReviewerId());

        review.setStatus(ReviewStatus.INVITATION_ACCEPTED);
        review.setAcceptedAt(LocalDateTime.now());
        reviewRepository.save(review);

        reviewEventPublisher.publishAccepted(
                ReviewAcceptedEvent.builder()
                        .reviewId(review.getId())
                        .paperId(review.getPaperId())
                        .reviewerId(review.getReviewerId())
                        .reviewerEmail(getReviewerEmail(review.getReviewerId()))
                        .acceptedAt(review.getAcceptedAt())
                        .build()

        );

        return reviewMapper.toResponse(review);
    }

    @Override
    public ReviewResponse declineInvitation(
            Long reviewId,
            DeclineReviewRequest request
    ) {
        Review review = lookupService.getReviewById(reviewId);
        authorizationService.verifyReviewer(review);
        validationService.validateInvitationDecline(review);
//        ReviewerSummaryResponse reviewer = authLookupService.getReviewer(review.getReviewerId());

        review.setStatus(ReviewStatus.INVITATION_DECLINED);
        review.setDeclineReason(request.getReason());
        review.setDeclinedAt(LocalDateTime.now());

        reviewRepository.save(review);

        reviewEventPublisher.publishDeclined(
                ReviewDeclinedEvent.builder()
                        .reviewId(review.getId())
                        .paperId(review.getPaperId())
                        .reviewerId(review.getReviewerId())
                        .reviewerEmail(getReviewerEmail(review.getReviewerId()))
                        .reason(request.getReason())
                        .declinedAt(review.getDeclinedAt())
                        .build()
        );

        return reviewMapper.toResponse(review);
    }

    @Override
    public ReviewResponse submitReview(
            Long reviewId,
            SubmitReviewRequest request
    ) {
        Review review = lookupService.getReviewById(reviewId);

        authorizationService.verifyReviewer(review);

        if (review.getStatus() == ReviewStatus.INVITATION_ACCEPTED) {

            review.setStatus(ReviewStatus.IN_PROGRESS);
        }

//        ReviewerSummaryResponse reviewer = authLookupService.getReviewer(review.getReviewerId());

        RecommendationValidationResult validationResult =
                validationService.validateSubmission(
                        review,
                        request
                );
        review.setCommentsForAuthor(request.getCommentsForAuthor());
        review.setCommentsForEditor(request.getCommentsForEditor());
        review.setOverallScore(request.getOverallScore());
        review.setRecommendation(request.getRecommendation());
        review.setStatus(ReviewStatus.SUBMITTED);
        review.setAttachmentUrl(request.getAttachmentUrl());
        review.setSubmittedAt(LocalDateTime.now());

        review.setRequiresEditorialAttention(
                validationResult.isRequiresAttention()
        );
        review.setEditorialAttentionReason(
                validationResult.getReason()
        );

        reviewRepository.save(review);

        reviewEventPublisher.publishSubmitted(
                ReviewSubmittedEvent.builder()
                        .reviewId(review.getId())
                        .paperId(review.getPaperId())
                        .reviewerId(review.getReviewerId())
                        .reviewerEmail(getReviewerEmail(review.getReviewerId()))
                        .recommendation(review.getRecommendation())
                        .overallScore(review.getOverallScore())
                        .submittedAt(review.getSubmittedAt())
                        .requiresEditorialAttention(
                                review.getRequiresEditorialAttention()
                        )
                        .editorialAttentionReason(
                                review.getEditorialAttentionReason()
                        )
                        .build()
        );

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

        PaperSummaryResponse paper = paperLookupService.getPaperSummary(review.getPaperId());

        /*
         * Preserve the previous decision before updating.
         */
        EditorialDecision previousDecision = review.getDecision();

        review.setDecision(request.getDecision());
        review.setCommentsForEditor(request.getComment());
        review.setDecisionAt(LocalDateTime.now());

        switch (request.getDecision()) {

            case ACCEPT ->
                    review.setStatus(
                            ReviewStatus.ACCEPTED
                    );

            case MINOR_REVISION, MAJOR_REVISION ->
                    review.setStatus(
                            ReviewStatus.REVISION_REQUESTED
                    );

            case REJECT ->
                    review.setStatus(
                            ReviewStatus.REJECTED
                    );

        }

        reviewRepository.save(review);

        decisionHistoryService.recordDecision(
                review,
                previousDecision,
                request.getDecision(),
                request.getComment(),
                currentUserService.getCurrentUser().getId()
        );

        researchStatusService.updatePaperStatus(
                review.getPaperId(),
                request.getDecision()
        );

        reviewEventPublisher.publishDecision(
                EditorialDecisionEvent.builder()
                        .reviewId(review.getId())
                        .paperId(review.getPaperId())
                        .authorId(review.getAuthorId())
                        .recipientEmail(paper.getAuthorEmail())
                        .decision(review.getDecision())
                        .editorId(currentUserService.getCurrentUser().getId())
                        .decisionAt(review.getDecisionAt())
                        .build()
        );

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

        CurrentUser currentUser = currentUserService.getCurrentUser();

        Page<ReviewSummaryResponse> content = reviews.map(review ->
                blindReviewService.maskSummary(toSummary(review), currentUser)
        );
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

        CurrentUser currentUser = currentUserService.getCurrentUser();

        Page<ReviewSummaryResponse> content = reviews.map(review ->
                blindReviewService.maskSummary(
                        toSummary(review),
                        currentUser
                )
        );

        return new PagedResponse<>(content);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponse getReview(
            Long reviewId
    ) {
        Review review = lookupService.getReviewById(reviewId);
        CurrentUser user = currentUserService.getCurrentUser();

        if ("EDITOR".equals(user.getRole())) {
            authorizationService.verifyEditor();
        }

        if ("REVIEWER".equals(user.getRole())) {
            authorizationService.verifyReviewer(review);
        }

        return blindReviewService.maskReview(review, user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevisionHistoryResponse> getRevisionHistory(
            Long paperId
    ) {
        authorizationService.verifyEditor();

        return revisionHistoryService.getRevisionHistory(
                paperId
        );
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new ReviewNotFoundException(
                                "Review not found"
                                )
                        );

        reviewAttachmentService.deleteAllReviewAttachments(reviewId);

        reviewRepository.delete(review);

        log.info(
                "Review deleted successfully. reviewId={}",
                reviewId
        );
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(
            Long reviewId,
            UpdateReviewRequest request
    ) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(
                        "Review not found"
                )
        );

        CurrentUser user = currentUserService.getCurrentUser();
        Long currentUserId = user.getId();
        String role = user.getRole();

        if ("REVIEWER".equals(role) &&
                !review.getReviewerId().equals(currentUserId)) {

            throw new AccessDeniedException("You can only update your own review");
        }

        review.setRecommendation(request.getRecommendation());
        review.setOverallScore(request.getOverallScore());
        review.setCommentsForAuthor(request.getCommentsForAuthor());
        review.setCommentsForEditor(request.getCommentsForEditor());
        review.setRequiresEditorialAttention(request.getRequiresEditorialAttention());
        review.setEditorialAttentionReason(request.getEditorialAttentionReason());

        Review saved = reviewRepository.save(review);

        return reviewMapper.toResponse(saved);
    }

}