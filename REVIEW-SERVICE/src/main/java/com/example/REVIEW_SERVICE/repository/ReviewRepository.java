package com.example.REVIEW_SERVICE.repository;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    boolean existsByPaperIdAndReviewerIdAndRevisionNumber(
            Long paperId,
            Long reviewerId,
            Integer revisionNumber
    );

    Optional<Review> findByPaperIdAndReviewerIdAndRevisionNumber(
            Long paperId,
            Long reviewerId,
            Integer revisionNumber
    );

    Page<Review> findByReviewerId(
            Long reviewerId,
            Pageable pageable
    );

    Page<Review> findByPaperId(
            Long paperId,
            Pageable pageable
    );

    Page<Review> findByStatus(
            ReviewStatus status,
            Pageable pageable
    );

    Page<Review> findByDeadlineBeforeAndStatus(
            LocalDateTime deadline,
            ReviewStatus status,
            Pageable pageable
    );

    Page<Review> findByPaperIdAndStatus(
            Long paperId,
            ReviewStatus status,
            Pageable pageable
    );

    long countByReviewerId(
            Long reviewerId
    );

    long countByReviewerIdAndStatus(
            Long reviewerId,
            ReviewStatus status
    );

    long countOverdueReviews(
            Long reviewerId,
            LocalDateTime deadline
    );

    long countByReviewerIdAndStatusIn(
            Long reviewerId,
            Collection<ReviewStatus> statuses
    );

    long countByPaperId(Long paperId);

    boolean existsByPaperIdAndReviewerId(Long paperId, Long reviewerId);

}