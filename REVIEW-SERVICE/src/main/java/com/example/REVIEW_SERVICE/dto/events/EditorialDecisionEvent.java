package com.example.REVIEW_SERVICE.dto.events;

import com.example.REVIEW_SERVICE.enums.EditorialDecision;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditorialDecisionEvent {

    private Long paperId;

    private Long reviewId;

    private Long editorId;

    private EditorialDecision decision;

}