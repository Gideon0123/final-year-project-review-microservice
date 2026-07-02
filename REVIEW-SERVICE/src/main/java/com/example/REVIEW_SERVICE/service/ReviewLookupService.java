package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewLookupService {

    Review getReviewById(Long reviewId);
    Page<Review> getReviewsForPaper(Long paperId, Pageable pageable);
    Page<Review> getReviewsForReviewer(Long reviewerId, Pageable pageable);

}

