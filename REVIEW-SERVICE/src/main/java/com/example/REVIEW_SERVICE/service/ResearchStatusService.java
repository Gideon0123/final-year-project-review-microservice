package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.enums.EditorialDecision;

public interface ResearchStatusService {

    void updatePaperStatus(
            Long paperId,
            EditorialDecision decision
    );

}