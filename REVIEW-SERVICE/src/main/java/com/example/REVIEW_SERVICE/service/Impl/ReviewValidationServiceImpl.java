package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.PaperSummaryResponse;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.enums.ResearchStatus;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import com.example.REVIEW_SERVICE.exception.DuplicateReviewerAssignmentException;
import com.example.REVIEW_SERVICE.exception.InvalidReviewStateException;
import com.example.REVIEW_SERVICE.exception.MaximumReviewersReachedException;
import com.example.REVIEW_SERVICE.exception.ReviewAlreadyCompletedException;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.ResearchPaperLookupService;
import com.example.REVIEW_SERVICE.service.ReviewValidationService;
import com.example.REVIEW_SERVICE.utils.ReviewValidationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewValidationServiceImpl implements ReviewValidationService {

    private final ReviewRepository reviewRepository;
    private final ResearchPaperLookupService researchPaperLookupService;

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
    public void validateSubmission(
            Review review
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
}