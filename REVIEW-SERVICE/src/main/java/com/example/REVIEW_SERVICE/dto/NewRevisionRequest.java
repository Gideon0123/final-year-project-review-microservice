package com.example.REVIEW_SERVICE.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewRevisionRequest {

    @NotNull
    private Long paperId;

    @NotNull
    @Min(2)
    private Integer revisionNumber;

    @NotNull
    private Long editorId;

}