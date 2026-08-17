package com.example.REVIEW_SERVICE.repository;

import com.example.REVIEW_SERVICE.entity.ReviewAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewAttachmentRepository
        extends JpaRepository<ReviewAttachment, Long> {

    List<ReviewAttachment> findByReviewId(Long reviewId);
    boolean existsByObjectKey(String objectKey);
    Optional<ReviewAttachment> findByIdAndReviewId(
            Long attachmentId,
            Long reviewId
    );

    List<ReviewAttachment> findAllByReviewId(Long reviewId);

    @Query("""
       select a.objectKey
       from ReviewAttachment a
       """)
    List<String> findAllObjectKeys();
}