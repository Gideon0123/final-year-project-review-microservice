package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.*;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.service.ResearchPaperLookupService;
import com.example.REVIEW_SERVICE.service.ReviewDashboardService;
import com.example.REVIEW_SERVICE.service.ReviewLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewDashboardServiceImpl implements ReviewDashboardService {

    private final ReviewLookupService lookupService;
    private final ResearchPaperLookupService paperLookupService;

    @Override
    public EditorDashboardResponse getDashboard(Long paperId) {
        PaperSummaryResponse paper = paperLookupService.getPaperSummary(
                paperId
        );

        List<Review> reviews = lookupService.getCurrentReviews(
                paperId
        );

        return EditorDashboardResponse.builder()
                .paperId(paper.getId())
                .title(paper.getTitle())
                .status(paper.getStatus())
                .currentRevision(paper.getRevisionNumber())
                .currentRound(
                        reviews.isEmpty()
                                ? 0
                                : reviews.getFirst().getReviewRound()
                )
                .activeReviews(
                        reviews.stream()
                                .map(this::toSummary)
                                .toList()
                )
                .build();
    }

    private ReviewSummaryResponse toSummary(
            Review review
    ) {
        return ReviewSummaryResponse.builder()
                .id(review.getId())
                .paperId(review.getPaperId())
                .reviewerId(review.getReviewerId())
                .status(review.getStatus())
                .deadline(review.getDeadline())
                .revisionNumber(review.getRevisionNumber())
//                .recommendation(review.getRecommendation())
                .build();

    }

}
