package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.AssignReviewerRequest;
import com.example.REVIEW_SERVICE.dto.ReviewResponse;
import com.example.REVIEW_SERVICE.payload.PagedResponse;

public interface ReviewService {

    ReviewResponse assignReviewer(
            AssignReviewerRequest request
    );

    ReviewResponse acceptInvitation(
            Long reviewId
    );

    ReviewResponse declineInvitation(
            Long reviewId,
            DeclineReviewRequest request
    );

    ReviewResponse submitReview(
            Long reviewId,
            SubmitReviewRequest request
    );

    ReviewResponse editorDecision(
            Long reviewId,
            EditorialDecisionRequest request
    );

    PagedResponse<ReviewSummaryResponse> getAssignedReviews(
            int page,
            int size
    );

    PagedResponse<ReviewSummaryResponse> getPaperReviews(
            Long paperId,
            int page,
            int size
    );

    ReviewResponse getReview(
            Long reviewId
    );

}