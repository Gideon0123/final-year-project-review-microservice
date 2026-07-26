package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.service.ReviewDeadlineService;
import com.example.REVIEW_SERVICE.utils.ReviewProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    @Override
    public boolean isOverdue(
            Review review
    ) {
        return LocalDateTime.now().isAfter(
                review.getDeadline()
        );
    }

    @Override
    public long daysRemaining(
            Review review
    ) {
        return ChronoUnit.DAYS.between(
                LocalDate.now(),
                review.getDeadline().toLocalDate()
        );
    }

    @Override
    public boolean canSubmit(
            Review review
    ) {
        return !isOverdue(review);
    }

}