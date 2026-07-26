package com.example.REVIEW_SERVICE.dto;

import com.example.REVIEW_SERVICE.enums.EditorialDecision;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDecisionHistoryResponse {

    private Long id;

    private EditorialDecision previousDecision;

    private EditorialDecision decision;

    private String comment;

    private Long decidedBy;

    private LocalDateTime decidedAt;

}
