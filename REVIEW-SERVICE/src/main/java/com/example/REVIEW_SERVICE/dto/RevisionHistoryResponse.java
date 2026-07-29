package com.example.REVIEW_SERVICE.dto;

import com.example.REVIEW_SERVICE.enums.EditorialDecision;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionHistoryResponse {

    private Integer revisionNumber;

    private Integer reviewRound;

    private EditorialDecision editorialDecision;

    private List<ReviewSummaryResponse> reviews;

}
