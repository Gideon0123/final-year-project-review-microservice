package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.ReviewStatisticsResponse;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.ReviewStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewStatisticsServiceImpl implements ReviewStatisticsService {

    private final ReviewRepository reviewRepository;

    private static final Set<ReviewStatus> PENDING_STATUSES = Set.of(
            ReviewStatus.PENDING_INVITATION,
            ReviewStatus.INVITATION_ACCEPTED,
            ReviewStatus.IN_PROGRESS
    );

    @Override
    public ReviewStatisticsResponse getReviewerStatistics(
            Long reviewerId
    ) {

        long pending = reviewRepository.countByReviewerIdAndStatusIn(
                reviewerId,
                PENDING_STATUSES
        );

        long completed = reviewRepository.countByReviewerIdAndStatus(
                reviewerId,
                ReviewStatus.COMPLETED
        );

        long overdue = reviewRepository.countOverdueReviews(
                reviewerId,
                LocalDateTime.now()
        );

        return ReviewStatisticsResponse.builder()
                .pendingReviews(pending)
                .completedReviews(completed)
                .overdueReviews(overdue)
                .build();
    }

}