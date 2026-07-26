package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.ReviewResponse;
import com.example.REVIEW_SERVICE.dto.ReviewSummaryResponse;
import com.example.REVIEW_SERVICE.entity.CurrentUser;
import com.example.REVIEW_SERVICE.entity.Review;

public interface BlindReviewService {

    ReviewResponse maskReview(
            Review review,
            CurrentUser currentUser
    );

    ReviewSummaryResponse maskSummary(
            ReviewSummaryResponse response,
            CurrentUser currentUser
    );

}