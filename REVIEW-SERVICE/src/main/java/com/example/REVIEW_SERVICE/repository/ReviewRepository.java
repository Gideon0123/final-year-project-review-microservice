package com.example.REVIEW_SERVICE.repository;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository
        extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

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

    List<Review> findByPaperId(
            Long paperId
    );

    List<Review> findByStatus(
            ReviewStatus status
    );

    List<Review> findByDeadlineBeforeAndStatus(
            LocalDateTime date,
            ReviewStatus status
    );

    List<Review> findByPaperIdAndStatus(
            Long paperId,
            ReviewStatus status
    );

    long countByReviewerId(
            Long reviewerId
    );

    long countByReviewerIdAndStatus(
            Long reviewerId,
            ReviewStatus status
    );
}
