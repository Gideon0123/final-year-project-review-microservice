package com.example.REVIEW_SERVICE.feign;

import com.example.REVIEW_SERVICE.config.FeignConfig;
import com.example.REVIEW_SERVICE.dto.ApiResponse;
import com.example.REVIEW_SERVICE.dto.PaperSummaryResponse;
import com.example.REVIEW_SERVICE.dto.ResearchStatusUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "RESEARCH-SERVICE",
        configuration = FeignConfig.class
)
public interface ResearchServiceClient {

    @GetMapping("/research/papers/{paperId}/summary")
    ApiResponse<PaperSummaryResponse> getPaperSummary(
            @PathVariable Long paperId
    );

    @PostMapping("/research/papers/internal/{paperId}/status")
    void updateStatus(
            @PathVariable Long paperId,
            @RequestBody ResearchStatusUpdateRequest request
    );

}