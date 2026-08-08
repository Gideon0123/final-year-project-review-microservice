package com.example.REVIEW_SERVICE.dto.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEscalationEvent {

    private Long reviewId;

    private Long reviewerId;

    private String reviewerEmail;

    private Long paperId;

    private LocalDateTime deadline;

    private LocalDateTime escalatedAt;

}