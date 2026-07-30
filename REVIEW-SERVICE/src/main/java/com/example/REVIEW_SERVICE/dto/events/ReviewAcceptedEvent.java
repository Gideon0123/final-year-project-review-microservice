package com.example.REVIEW_SERVICE.dto.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAcceptedEvent {

    private Long reviewId;

    private Long paperId;

    private String reviewerEmail;

    private Long reviewerId;

    private LocalDateTime acceptedAt;

}