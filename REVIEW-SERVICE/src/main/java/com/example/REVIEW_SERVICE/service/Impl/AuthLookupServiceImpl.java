package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.ApiResponse;
import com.example.REVIEW_SERVICE.dto.ReviewerSummaryResponse;
import com.example.REVIEW_SERVICE.exception.ReviewerNotFoundException;
import com.example.REVIEW_SERVICE.feign.AuthServiceClient;
import com.example.REVIEW_SERVICE.service.AuthLookupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthLookupServiceImpl implements AuthLookupService {

    private final AuthServiceClient authServiceClient;

    @Override
    public ReviewerSummaryResponse getReviewer(
            Long reviewerId
    ) {

        ApiResponse<ReviewerSummaryResponse> response =
                authServiceClient.getReviewer(reviewerId);

        if (response == null || !response.isSuccess() || response.getData() == null) {

            throw new ReviewerNotFoundException("Reviewer not found.");
        }

        return response.getData();
    }

}