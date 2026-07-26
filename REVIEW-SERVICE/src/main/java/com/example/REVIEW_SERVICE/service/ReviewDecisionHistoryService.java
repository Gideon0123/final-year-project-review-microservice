package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.enums.EditorialDecision;

public interface ReviewDecisionHistoryService {

    void recordDecision(
            Review review,
            EditorialDecision previousDecision,
            EditorialDecision newDecision,
            String comment,
            Long editorId
    );

}