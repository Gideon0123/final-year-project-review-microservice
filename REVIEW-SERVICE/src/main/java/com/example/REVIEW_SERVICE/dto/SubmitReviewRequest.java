package com.example.REVIEW_SERVICE.dto;

import com.example.REVIEW_SERVICE.enums.ReviewRecommendation;
import com.example.REVIEW_SERVICE.enums.ReviewScore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitReviewRequest {

    @NotNull
    private ReviewRecommendation recommendation;

    @NotNull
    private ReviewScore overallScore;

    @NotBlank
    @Size(max = 10000)
    private String commentsForAuthor;

    @Size(max = 10000)
    private String commentsForEditor;

}