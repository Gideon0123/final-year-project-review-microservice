package com.example.REVIEW_SERVICE.dto.events;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionRequestedEvent {

    private Long paperId;

    private Integer revisionNumber;

}