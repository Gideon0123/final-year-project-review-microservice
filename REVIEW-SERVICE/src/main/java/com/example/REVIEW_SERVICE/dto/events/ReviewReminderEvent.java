package com.example.REVIEW_SERVICE.dto.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReminderEvent {

    private Long reviewId;

    private Long reviewerId;

    private Long paperId;

    private LocalDateTime deadline;

}