package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.ReviewerSummaryResponse;

public interface AuthLookupService {

    ReviewerSummaryResponse getReviewer(
            Long reviewerId
    );

}