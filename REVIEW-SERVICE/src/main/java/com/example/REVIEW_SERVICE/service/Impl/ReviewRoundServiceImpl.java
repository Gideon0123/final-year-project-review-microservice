package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.ReviewRoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewRoundServiceImpl implements ReviewRoundService {

    private final ReviewRepository reviewRepository;

    @Override
    public Integer determineNextRound(
            Long paperId
    ) {
        return getCurrentRound(paperId) + 1;

    }

    @Override
    public Integer getCurrentRound(
            Long paperId
    ) {

        return reviewRepository.findHighestReviewRound(
                paperId
        ).orElse(0) + 1;

    }

}