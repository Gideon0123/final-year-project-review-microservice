package com.example.REVIEW_SERVICE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendationValidationResult {

    private final boolean requiresAttention;

    private final String reason;

}