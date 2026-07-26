package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.ReviewResponse;
import com.example.REVIEW_SERVICE.dto.ReviewSummaryResponse;
import com.example.REVIEW_SERVICE.entity.CurrentUser;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.mapper.ReviewMapper;
import com.example.REVIEW_SERVICE.service.BlindReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlindReviewServiceImpl implements BlindReviewService {

    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponse maskReview(
            Review review,
            CurrentUser currentUser
    ) {
        ReviewResponse response = reviewMapper.toResponse(review);

        switch (currentUser.getRole()) {

            case "REVIEWER" -> {
                maskForReviewer(response);
                return response;
            }

            case "AUTHOR" -> {
                maskForAuthor(response);
                return response;
            }

            default -> {
                return response;
            }

        }

    }

    @Override
    public ReviewSummaryResponse maskSummary(
            ReviewSummaryResponse response,
            CurrentUser currentUser
    ) {

        switch (currentUser.getRole()) {

            case "AUTHOR" -> {
                response.setReviewerId(null);
            }

            case "REVIEWER" -> {
            /*
             Nothing to hide.
             */
            }

            case "ADMIN" -> {
            /*
             Editors see everything.
             */
            }

        }

        return response;

    }

    private void maskForAuthor(
            ReviewResponse response
    ) {
        response.setReviewerId(null);
        response.setCommentsForEditor(null);
    }

    private void maskForReviewer(
            ReviewResponse response
    ) {

        /*
         Nothing to hide yet.

         Later this method will hide author details
         once author metadata is included through
         Research Service.
        */

    }

}
