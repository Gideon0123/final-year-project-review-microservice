package com.example.REVIEW_SERVICE.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcceptReviewRequest {

    /**
     * Optional message.
     */
    private String note;

}