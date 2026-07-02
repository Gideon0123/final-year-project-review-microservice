package com.example.REVIEW_SERVICE.feign;

import com.example.REVIEW_SERVICE.config.FeignConfig;
import com.example.REVIEW_SERVICE.dto.ApiResponse;
import com.example.REVIEW_SERVICE.dto.PaperSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "RESEARCH-SERVICE",
        configuration = FeignConfig.class
)
public interface ResearchServiceClient {

    @GetMapping("/research/papers/{paperId}/summary")
    ApiResponse<PaperSummaryResponse> getPaperSummary(
            @PathVariable Long paperId
    );

}