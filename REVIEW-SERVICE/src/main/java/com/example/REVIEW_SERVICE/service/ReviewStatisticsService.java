package com.example.REVIEW_SERVICE.service;

public interface ReviewStatisticsService {

    long pendingReviews(Long reviewerId);
    long completedReviews(Long reviewerId);

}