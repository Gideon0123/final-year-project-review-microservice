package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.exception.ReviewNotFoundException;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.ReviewLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewLookupServiceImpl implements ReviewLookupService {

    private final ReviewRepository reviewRepository;

    @Override
    public Review getReviewById(
            Long reviewId
    ) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ReviewNotFoundException(
                                "Review with ID " +
                                        reviewId +
                                        " was not found."
                        )
                );
    }

    @Override
    public Page<Review> getReviewsForPaper(
            Long paperId,
            Pageable pageable
    ) {
        return reviewRepository.findByPaperId(
                paperId,
                pageable
        );
    }

    @Override
    public Page<Review> getReviewsForReviewer(
            Long reviewerId,
            Pageable pageable
    ) {
        return reviewRepository.findByReviewerId(
                reviewerId,
                pageable
        );
    }

}