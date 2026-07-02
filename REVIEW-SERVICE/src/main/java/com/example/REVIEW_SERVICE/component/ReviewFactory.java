package com.example.REVIEW_SERVICE.component;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewFactory {

    public Review createAssignment(
            Long paperId,
            Long reviewerId,
            Long assignedBy
    ) {

        return Review.builder()
                .paperId(paperId)
                .reviewerId(reviewerId)
                .assignedBy(assignedBy)
                .status(ReviewStatus.PENDING_INVITATION)
                .assignedAt(LocalDateTime.now())

                .build();

    }

}
