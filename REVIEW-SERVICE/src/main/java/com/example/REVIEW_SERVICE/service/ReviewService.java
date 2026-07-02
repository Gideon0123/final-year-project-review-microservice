package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.AssignReviewerRequest;
import com.example.REVIEW_SERVICE.dto.ReviewResponse;

import java.util.List;

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
            EditorDecisionRequest request
    );

    List<ReviewResponse> getAssignedReviews();

    List<ReviewResponse> getPaperReviews(
            Long paperId
    );

    ReviewResponse getReview(
            Long reviewId
    );

}