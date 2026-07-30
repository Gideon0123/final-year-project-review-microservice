package com.example.REVIEW_SERVICE.dto.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDeclinedEvent {

    private Long reviewId;

    private Long paperId;

    private Long reviewerId;

    private String reviewerEmail;

    private String reason;

    private LocalDateTime declinedAt;

}