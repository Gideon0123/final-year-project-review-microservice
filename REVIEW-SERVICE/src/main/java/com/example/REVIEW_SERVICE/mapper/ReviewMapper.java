package com.example.REVIEW_SERVICE.mapper;

import com.example.REVIEW_SERVICE.dto.ReviewResponse;
import com.example.REVIEW_SERVICE.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewMapper {

    ReviewResponse toResponse(
            Review review
    );

    List<ReviewResponse> toResponseList(
            List<Review> reviews
    );
}