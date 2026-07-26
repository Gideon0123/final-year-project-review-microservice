package com.example.REVIEW_SERVICE.repository;

import com.example.REVIEW_SERVICE.entity.ReviewDecisionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDecisionHistoryRepository
        extends JpaRepository<ReviewDecisionHistory, Long> {

    List<ReviewDecisionHistory> findByReviewIdOrderByDecidedAtDesc(
            Long reviewId
    );

}