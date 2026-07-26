package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.Review;

import java.time.LocalDateTime;

public interface ReviewDeadlineService {

    LocalDateTime calculateDeadline();
    boolean isOverdue(Review review);
    long daysRemaining(Review review);
    boolean canSubmit(Review review);

}