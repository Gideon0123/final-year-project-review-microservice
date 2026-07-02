package com.example.REVIEW_SERVICE.feign;

import com.example.REVIEW_SERVICE.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "RESEARCH-SERVICE",
        configuration = FeignConfig.class
)
public interface ResearchServiceClient {
}