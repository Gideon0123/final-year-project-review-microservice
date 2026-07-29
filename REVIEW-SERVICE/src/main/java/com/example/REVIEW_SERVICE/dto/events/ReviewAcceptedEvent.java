package com.example.REVIEW_SERVICE.dto.events;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAcceptedEvent {

    private Long reviewId;

    private Long paperId;

    private Long reviewerId;

}