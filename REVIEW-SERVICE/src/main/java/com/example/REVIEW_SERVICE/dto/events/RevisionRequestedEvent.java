package com.example.REVIEW_SERVICE.dto.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionRequestedEvent {

    private Long paperId;

    private Long authorId;

    private Integer revisionNumber;

    private LocalDateTime submittedAt;

}