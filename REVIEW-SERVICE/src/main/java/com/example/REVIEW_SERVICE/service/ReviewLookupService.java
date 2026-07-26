package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.entity.ReviewDecisionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewLookupService {

    Review getReviewById(Long reviewId);
    Page<Review> getReviewsForPaper(Long paperId, Pageable pageable);
    Page<Review> getReviewsForReviewer(Long reviewerId, Pageable pageable);
    List<ReviewDecisionHistory> getDecisionHistory(Long reviewId);

}

