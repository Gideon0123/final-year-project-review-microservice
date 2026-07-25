package com.example.REVIEW_SERVICE.feign;

import com.example.REVIEW_SERVICE.config.FeignConfig;
import com.example.REVIEW_SERVICE.dto.ApiResponse;
import com.example.REVIEW_SERVICE.dto.ReviewerSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "AUTH-SERVICE",
        configuration = FeignConfig.class
)
public interface AuthServiceClient {

    @GetMapping("/users/internal/reviewers/{id}")
    ApiResponse<ReviewerSummaryResponse> getReviewer(
            @PathVariable Long id
    );

}