package com.example.REVIEW_SERVICE.dto;

import com.example.REVIEW_SERVICE.enums.EditorialDecision;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditorialDecisionRequest {

    @NotNull
    private EditorialDecision decision;

    @Size(max = 5000)
    private String comment;

}