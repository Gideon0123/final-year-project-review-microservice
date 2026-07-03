package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.ReviewStatisticsResponse;

public interface ReviewStatisticsService {

    ReviewStatisticsResponse getReviewerStatistics(
            Long reviewerId
    );

}