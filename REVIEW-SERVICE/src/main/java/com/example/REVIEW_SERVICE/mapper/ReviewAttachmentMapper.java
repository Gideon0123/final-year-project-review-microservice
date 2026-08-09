package com.example.REVIEW_SERVICE.mapper;

import com.example.REVIEW_SERVICE.dto.ReviewAttachmentResponse;
import com.example.REVIEW_SERVICE.entity.ReviewAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewAttachmentMapper {

    @Mapping(
            target = "reviewId",
            source = "review.id"
    )
    ReviewAttachmentResponse toResponse(
            ReviewAttachment attachment
    );
}