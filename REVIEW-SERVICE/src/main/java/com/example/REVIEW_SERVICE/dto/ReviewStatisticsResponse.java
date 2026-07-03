package com.example.REVIEW_SERVICE.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewStatisticsResponse {

    private long pendingReviews;

    private long completedReviews;

    private long overdueReviews;

}