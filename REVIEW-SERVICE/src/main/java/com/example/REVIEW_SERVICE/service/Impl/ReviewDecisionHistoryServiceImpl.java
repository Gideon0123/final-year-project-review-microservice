package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.entity.ReviewDecisionHistory;
import com.example.REVIEW_SERVICE.enums.EditorialDecision;
import com.example.REVIEW_SERVICE.repository.ReviewDecisionHistoryRepository;
import com.example.REVIEW_SERVICE.service.ReviewDecisionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewDecisionHistoryServiceImpl implements ReviewDecisionHistoryService {

    private final ReviewDecisionHistoryRepository repository;

    @Override
    public void recordDecision(
            Review review,
            EditorialDecision previousDecision,
            EditorialDecision newDecision,
            String comment,
            Long editorId
    ) {

        ReviewDecisionHistory history =
                ReviewDecisionHistory.builder()
                        .review(review)
                        .previousDecision(previousDecision)
                        .decision(newDecision)
                        .comment(comment)
                        .decidedBy(editorId)
                        .build();

        repository.save(history);

    }

}