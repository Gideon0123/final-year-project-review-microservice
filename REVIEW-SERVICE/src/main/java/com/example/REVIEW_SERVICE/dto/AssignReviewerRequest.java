package com.example.REVIEW_SERVICE.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignReviewerRequest {

    @NotNull
    private Long reviewerId;

    @NotNull
    @Future
    private LocalDateTime deadline;

}