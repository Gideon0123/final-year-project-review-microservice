package com.example.REVIEW_SERVICE.dto;

import com.example.REVIEW_SERVICE.enums.ResearchStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResearchStatusUpdateRequest {

    private ResearchStatus status;

}