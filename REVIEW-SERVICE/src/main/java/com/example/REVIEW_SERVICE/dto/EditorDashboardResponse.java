package com.example.REVIEW_SERVICE.dto;

import com.example.REVIEW_SERVICE.enums.ResearchStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditorDashboardResponse {

    private Long paperId;

    private String title;

    private Integer currentRevision;

    private Integer currentRound;

    private ResearchStatus status;

    private List<ReviewSummaryResponse> activeReviews;

}