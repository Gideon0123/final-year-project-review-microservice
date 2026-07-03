package com.example.REVIEW_SERVICE.mapper;

import com.example.REVIEW_SERVICE.dto.ReviewResponse;
import com.example.REVIEW_SERVICE.dto.ReviewSummaryResponse;
import com.example.REVIEW_SERVICE.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewMapper {

    ReviewResponse toResponse(
            Review review
    );

//    ReviewSummaryResponse toSummary(
//            Review review
//    );

}