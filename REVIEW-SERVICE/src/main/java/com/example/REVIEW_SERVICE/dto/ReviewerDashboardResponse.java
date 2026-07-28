package com.example.REVIEW_SERVICE.dto;

import com.example.REVIEW_SERVICE.enums.EditorialDecision;
import com.example.REVIEW_SERVICE.enums.ReviewRecommendation;
import com.example.REVIEW_SERVICE.enums.ReviewScore;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerDashboardResponse {

    private Long reviewId;
    private Long reviewerId;
    private ReviewRecommendation recommendation;
    private ReviewScore overallScore;
    private ReviewStatus status;
    private boolean editorialAttentionRequired;
    private String editorialAttentionReason;
    private LocalDateTime deadline;
    private EditorialDecision decision;
    private LocalDateTime submittedAt;
//    private boolean submitted;

}

