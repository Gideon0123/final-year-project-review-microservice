package com.example.REVIEW_SERVICE.dto;

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
public class ReviewResponse {

    private Long id;

    private Long paperId;

    private Long reviewerId;

    private Integer revisionNumber;

    private ReviewStatus status;

    private ReviewRecommendation recommendation;

    private ReviewScore overallScore;

    private String commentsForAuthor;

    private String commentsForEditor;

    private LocalDateTime deadline;

    private LocalDateTime submittedAt;

}