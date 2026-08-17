package com.example.REVIEW_SERVICE.dto;

import com.example.REVIEW_SERVICE.enums.ReviewRecommendation;
import com.example.REVIEW_SERVICE.enums.ReviewScore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReviewRequest {

    private ReviewRecommendation recommendation;
    private ReviewScore overallScore;
    private String commentsForAuthor;
    private String commentsForEditor;
    private Boolean requiresEditorialAttention;
    private String editorialAttentionReason;

}