package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.Review;

import java.time.LocalDateTime;

public interface ReviewDeadlineService {

    LocalDateTime calculateDeadline();
    boolean isExpired(Review review);

}