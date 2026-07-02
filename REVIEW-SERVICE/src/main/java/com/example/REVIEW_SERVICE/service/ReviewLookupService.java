package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.Review;

import java.util.List;

public interface ReviewLookupService {

    Review getReviewById(
            Long reviewId
    );

    List<Review> getReviewsForPaper(
            Long paperId
    );

    List<Review> getReviewsForReviewer(
            Long reviewerId
    );

}