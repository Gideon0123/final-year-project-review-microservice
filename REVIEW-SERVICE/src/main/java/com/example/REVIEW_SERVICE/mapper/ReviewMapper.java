package com.example.REVIEW_SERVICE.mapper;

import com.example.REVIEW_SERVICE.dto.ReviewDecisionHistoryResponse;
import com.example.REVIEW_SERVICE.dto.ReviewResponse;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.entity.ReviewDecisionHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.temporal.ChronoUnit;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = ChronoUnit.class
)
public interface ReviewMapper {

    @Mapping(
            target = "overdue",
            expression = "java(review.getDeadline() != null && java.time.LocalDateTime.now().isAfter(review.getDeadline()))"
    )
    @Mapping(
            target = "daysRemaining",
            expression = "java(ChronoUnit.DAYS.between(java.time.LocalDate.now(), review.getDeadline().toLocalDate()))"
    )
    @Mapping(
            target = "hasAttachment",
            expression = "java(review.getAttachmentUrl() != null)"
    )
    ReviewResponse toResponse(Review review);

    ReviewDecisionHistoryResponse toDecisionHistoryResponse(
            ReviewDecisionHistory history
    );

}