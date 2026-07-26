package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.RecommendationValidationResult;
import com.example.REVIEW_SERVICE.dto.SubmitReviewRequest;
import com.example.REVIEW_SERVICE.entity.Review;

public interface ReviewValidationService {

    void validateAssignment(Long paperId, Long reviewerId);
    RecommendationValidationResult validateSubmission(Review review, SubmitReviewRequest request);
    void validateDecision(Review review);
    void validateInvitationAcceptance(Review review);
    void validateInvitationDecline(Review review);
}