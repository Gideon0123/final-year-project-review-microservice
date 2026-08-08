package com.example.REVIEW_SERVICE.repository;

import com.example.REVIEW_SERVICE.entity.ReviewAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewAttachmentRepository
        extends JpaRepository<ReviewAttachment, UUID> {

    List<ReviewAttachment> findByReviewId(Long reviewId);

    boolean existsByObjectKey(String objectKey);

}