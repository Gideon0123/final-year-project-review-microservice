package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.ReviewStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewStatisticsServiceImpl implements ReviewStatisticsService {

    private final ReviewRepository reviewRepository;

    private static final Set<ReviewStatus> PENDING_STATUSES =
            Set.of(
                    ReviewStatus.PENDING_INVITATION,
                    ReviewStatus.INVITATION_ACCEPTED,
                    ReviewStatus.IN_PROGRESS
            );

    @Override
    public long pendingReviews(
            Long reviewerId
    ) {

        return reviewRepository.countByReviewerIdAndStatusIn(
                reviewerId,
                PENDING_STATUSES
        );

    }

    @Override
    public long completedReviews(
            Long reviewerId
    ) {

        return reviewRepository.countByReviewerIdAndStatus(
                reviewerId,
                ReviewStatus.COMPLETED
        );

    }

}