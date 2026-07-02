package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.ReviewResponse;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlindReviewServiceImpl implements BlindReviewService {

    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponse maskForAuthor(
            Review review
    ) {

        ReviewResponse response = reviewMapper.toResponse(review);

        response.setReviewerId(null);

        return response;

    }

    @Override
    public ReviewResponse maskForReviewer(
            Review review
    ) {
        ReviewResponse response = reviewMapper.toResponse(review);

//        response.setAuthorId(null);
        response.setReviewerId(null);

        return response;

    }

}