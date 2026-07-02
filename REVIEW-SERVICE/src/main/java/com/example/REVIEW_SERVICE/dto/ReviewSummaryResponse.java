package com.example.REVIEW_SERVICE.dto;

import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSummaryResponse {

    private Long id;

    private Long paperId;

    private Long reviewerId;

    private Integer revisionNumber;

    private ReviewStatus status;

    private LocalDateTime deadline;

}