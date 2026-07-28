package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.ReviewDeadlineService;
import com.example.REVIEW_SERVICE.service.ReviewReassignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewReassignmentServiceImpl implements ReviewReassignmentService {

    private final ReviewRepository reviewRepository;
    private final ReviewDeadlineService deadlineService;

    @Override
    public void reassignPreviousReviewers(
            Long paperId,
            Integer revisionNumber
    ) {

        List<Review> previousReviews = reviewRepository.findLatestRoundReviews(paperId);

        if (previousReviews.isEmpty()) {
            return;
        }

        Integer nextRound = reviewRepository.findHighestReviewRound(paperId)
                .orElse(0) + 1;

        for (Review previous : previousReviews) {

            Review review =
                    Review.builder()
                            .paperId(previous.getPaperId())
                            .reviewerId(previous.getReviewerId())
                            .assignedBy(previous.getAssignedBy())
                            .revisionNumber(revisionNumber)
                            .reviewRound(nextRound)
                            .status(ReviewStatus.PENDING_INVITATION)
                            .deadline(deadlineService.calculateDeadline())
                            .assignedAt(LocalDateTime.now())
                            .invitationSentAt(LocalDateTime.now())
                            .createdAt(LocalDateTime.now())

                            .build();

            reviewRepository.save(review);

        }

    }

}