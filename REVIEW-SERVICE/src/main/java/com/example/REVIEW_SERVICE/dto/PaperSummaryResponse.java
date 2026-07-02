package com.example.REVIEW_SERVICE.dto;

import com.example.REVIEW_SERVICE.enums.ResearchStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperSummaryResponse {

    private Long id;

    private Long authorId;

    private String title;

    private ResearchStatus status;

    private Integer revisionNumber;

}