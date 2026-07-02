package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.Review;

public interface ReviewValidationService {

    void validateAssignment(Long paperId, Long reviewerId);
    void validateSubmission(Review review);
    void validateDecision(Review review);
}