package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.service.ReviewDeadlineService;
import com.example.REVIEW_SERVICE.utils.ReviewProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewDeadlineServiceImpl implements ReviewDeadlineService {

    private final ReviewProperties reviewProperties;

    /**
     * Calculates the review deadline
     * from the current date/time.
     */
    @Override
    public LocalDateTime calculateDeadline() {

        return LocalDateTime.now()
                .plusDays(
                        reviewProperties.getDeadlineDays()
                );

    }

    /**
     * Determines whether the review
     * deadline has expired.
     */
    @Override
    public boolean isExpired(
            Review review
    ) {

        LocalDateTime deadline = review.getDeadline();

        if (deadline == null) {
            return false;
        }

        return LocalDateTime.now()
                .isAfter(deadline);

    }

}