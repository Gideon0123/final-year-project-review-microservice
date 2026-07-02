package com.example.REVIEW_SERVICE.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclineReviewRequest {

    @NotBlank
    @Size(max = 1000)
    private String reason;

}