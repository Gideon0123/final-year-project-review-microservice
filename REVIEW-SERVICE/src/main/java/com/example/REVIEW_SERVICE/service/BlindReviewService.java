package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.ReviewResponse;
import com.example.REVIEW_SERVICE.entity.Review;

public interface BlindReviewService {

    ReviewResponse maskForAuthor(Review review);
    ReviewResponse maskForReviewer(Review review);

}