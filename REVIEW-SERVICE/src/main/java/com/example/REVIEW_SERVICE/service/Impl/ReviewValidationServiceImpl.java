package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.PaperSummaryResponse;
import com.example.REVIEW_SERVICE.dto.RecommendationValidationResult;
import com.example.REVIEW_SERVICE.dto.ReviewerSummaryResponse;
import com.example.REVIEW_SERVICE.dto.SubmitReviewRequest;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.enums.ResearchStatus;
import com.example.REVIEW_SERVICE.enums.ReviewRecommendation;
import com.example.REVIEW_SERVICE.enums.ReviewScore;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import com.example.REVIEW_SERVICE.exception.*;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.AuthLookupService;
import com.example.REVIEW_SERVICE.service.ResearchPaperLookupService;
import com.example.REVIEW_SERVICE.service.ReviewDeadlineService;
import com.example.REVIEW_SERVICE.service.ReviewValidationService;
import com.example.REVIEW_SERVICE.utils.ReviewValidationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewValidationServiceImpl implements ReviewValidationService {

    private final ReviewRepository reviewRepository;
    private final ResearchPaperLookupService researchPaperLookupService;
    private final AuthLookupService authLookupService;
    private final ReviewDeadlineService deadlineService;
    private static final Set<ReviewStatus> ACTIVE_REVIEW_STATUSES =
            Set.of(
                    ReviewStatus.PENDING_INVITATION,
                    ReviewStatus.INVITATION_ACCEPTED,
                    ReviewStatus.IN_PROGRESS
            );

    private RecommendationValidationResult validateRecommendationConsistency(
            SubmitReviewRequest request
    ) {

        ReviewScore score = request.getOverallScore();
        ReviewRecommendation recommendation =
                request.getRecommendation();

        if (score == null || recommendation == null) {

            return new RecommendationValidationResult(
                    false,
                    null
            );

        }

        switch (score) {

            case FIVE -> {

                if (recommendation == ReviewRecommendation.REJECT) {

                    return new RecommendationValidationResult(
                            true,
                            "Reviewer rated the paper as EXCELLENT but recommended REJECTION."
                    );

                }

            }

            case ONE -> {

                if (recommendation == ReviewRecommendation.ACCEPT) {

                    return new RecommendationValidationResult(
                            true,
                            "Reviewer rated the paper as POOR but recommended ACCEPTANCE."
                    );

                }

            }

        }

        return new RecommendationValidationResult(
                false,
                null
        );

    }

    private void validateDeadline(
            Review review
    ) {

        if (deadlineService.isOverdue(review)) {
            throw new InvalidReviewStateException("Review deadline has expired.");
        }

    }

    private void validateReviewerWorkload(
            Long reviewerId
    ) {

        long activeReviews =
                reviewRepository.countByReviewerIdAndStatusIn(
                        reviewerId,
                        ACTIVE_REVIEW_STATUSES
                );

        if (activeReviews >= ReviewValidationConstants.MAX_ACTIVE_REVIEWS) {

            throw new ReviewerNotEligibleException(
                    "Reviewer has reached the maximum active review workload."
            );

        }

    }

    private void validateReviewerEligibility(
            ReviewerSummaryResponse reviewer
    ) {

        if (!reviewer.isEnabled()) {
            throw new ReviewerNotEligibleException(
                    "Reviewer account is disabled."
            );
        }

        if (!reviewer.isEmailVerified()) {
            throw new ReviewerNotEligibleException(
                    "Reviewer email has not been verified."
            );
        }

        if (!reviewer.isAccountNonLocked()) {
            throw new ReviewerNotEligibleException(
                    "Reviewer account is locked."
            );
        }

        if (!"REVIEWER".equalsIgnoreCase(reviewer.getRole())) {
            throw new ReviewerNotEligibleException(
                    "Selected user is not a reviewer."
            );
        }
    }

    @Override
    public void validateAssignment(
            Long paperId,
            Long reviewerId
    ) {

        /*
         * Verify paper exists.
         */
        PaperSummaryResponse paper = researchPaperLookupService.getPaperSummary(
                paperId
        );

        ReviewerSummaryResponse reviewer = authLookupService.getReviewer(
                reviewerId
        );

        validateReviewerEligibility(reviewer);

        if (paper.getAuthorId().equals(reviewerId)) {

            throw new ReviewerNotEligibleException(
                    "Authors cannot review their own papers."
            );

        }

        validateReviewerWorkload(reviewerId);

        /*
         * Paper must be submitted.
         */
        if (paper.getStatus() != ResearchStatus.SUBMITTED &&
                paper.getStatus() != ResearchStatus.UNDER_REVIEW) {

            throw new InvalidReviewStateException(
                    "Paper cannot be assigned for review."
            );

        }

        /*
         * Reviewer already assigned?
         */
        if (reviewRepository.existsByPaperIdAndReviewerId(
                paperId,
                reviewerId
        )) {
            throw new DuplicateReviewerAssignmentException(
                    "Reviewer already assigned to this paper."
            );

        }

        /*
         * Maximum reviewers reached?
         */
        long totalReviews = reviewRepository.countByPaperId(paperId);

        if (totalReviews >= ReviewValidationConstants.MAX_REVIEWERS) {
            throw new MaximumReviewersReachedException("Maximum reviewers already assigned.");
        }
    }

    @Override
    public RecommendationValidationResult validateSubmission(
            Review review,
            SubmitReviewRequest request
    ) {
        if (review.getStatus() == ReviewStatus.COMPLETED) {
            throw new ReviewAlreadyCompletedException("Review has already been submitted.");
        }

        if (review.getStatus() == ReviewStatus.INVITATION_DECLINED) {
            throw new InvalidReviewStateException("Declined reviews cannot be submitted.");
        }

        if (review.getStatus() != ReviewStatus.INVITATION_ACCEPTED &&
                review.getStatus() != ReviewStatus.IN_PROGRESS) {

            throw new InvalidReviewStateException("Review is not ready for submission.");
        }

        if (request.getRecommendation() == null) {
            throw new InvalidReviewStateException("Recommendation is required.");
        }

        if (request.getOverallScore() == null) {
            throw new InvalidReviewStateException("Overall score is required.");
        }

        if (request.getCommentsForAuthor() == null ||
                request.getCommentsForAuthor().isBlank()) {

            throw new InvalidReviewStateException("Comments for author are required.");
        }

        String attachment = request.getAttachmentUrl();

        if (attachment != null && !attachment.isBlank()) {

            if (!(attachment.startsWith("http://")
                    || attachment.startsWith("https://"))) {

                throw new InvalidReviewStateException(
                        "Attachment URL must be a valid HTTP or HTTPS URL."
                );
            }
        }

        validateDeadline(review);

        return validateRecommendationConsistency(request);
    }

    @Override
    public void validateDecision(
            Review review
    ) {
        if (review.getStatus() != ReviewStatus.COMPLETED) {
            throw new InvalidReviewStateException(
                    "Editorial decision can only be made after review completion."
            );
        }
    }

    @Override
    public void validateInvitationAcceptance(
            Review review
    ) {
        if (review.getStatus() != ReviewStatus.PENDING_INVITATION) {

            throw new InvalidReviewStateException(
                    "Review invitation is no longer available."
            );
        }
    }

    @Override
    public void validateInvitationDecline(
            Review review
    ) {
        if (review.getStatus() != ReviewStatus.PENDING_INVITATION) {

            throw new InvalidReviewStateException(
                    "Review invitation is no longer available."
            );
        }
    }
}