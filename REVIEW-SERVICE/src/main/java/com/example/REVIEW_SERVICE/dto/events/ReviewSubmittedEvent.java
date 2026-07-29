package com.example.REVIEW_SERVICE.dto.events;

import com.example.REVIEW_SERVICE.enums.ReviewRecommendation;
import com.example.REVIEW_SERVICE.enums.ReviewScore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSubmittedEvent {

    private Long reviewId;

    private Long paperId;

    private Long reviewerId;

    private ReviewRecommendation recommendation;

    private ReviewScore overallScore;

    private LocalDateTime submittedAt;

    private Boolean requiresEditorialAttention;

    private String editorialAttentionReason;

}