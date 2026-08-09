package com.example.REVIEW_SERVICE.repository;

import com.example.REVIEW_SERVICE.entity.ReviewAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewAttachmentRepository
        extends JpaRepository<ReviewAttachment, Long> {

    List<ReviewAttachment> findByReviewId(Long reviewId);

    boolean existsByObjectKey(String objectKey);
}