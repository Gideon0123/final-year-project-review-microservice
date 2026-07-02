package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.PaperSummaryResponse;

public interface ResearchPaperLookupService {
    PaperSummaryResponse getPaperSummary(Long paperId);
}