package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.PaperDashboardResponse;
import com.example.REVIEW_SERVICE.dto.PaperSummaryResponse;
import com.example.REVIEW_SERVICE.dto.ReviewerDashboardResponse;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.ResearchPaperLookupService;
import com.example.REVIEW_SERVICE.service.ReviewDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewDashboardServiceImpl implements ReviewDashboardService {

    private final ReviewRepository reviewRepository;
    private final ResearchPaperLookupService researchPaperLookupService;

    @Override
    public PaperDashboardResponse getDashboard(
            Long paperId
    ) {
        PaperSummaryResponse paper = researchPaperLookupService.getPaperSummary(
                paperId
        );
        List<Review> reviews = reviewRepository.findByPaperId(
                paperId
        );

        List<ReviewerDashboardResponse> response =
                reviews.stream()
                        .map(this::toDashboard)
                        .toList();

        return PaperDashboardResponse.builder()
                .paperId(paper.getId())
                .title(paper.getTitle())
                .authorId(paper.getAuthorId())
                .researchStatus(paper.getStatus())
                .revisionNumber(paper.getRevisionNumber())
                .reviews(response)
                .build();

    }

    private ReviewerDashboardResponse toDashboard(
            Review review
    ) {

        return ReviewerDashboardResponse.builder()
                .reviewId(review.getId())
                .reviewerId(review.getReviewerId())
                .recommendation(review.getRecommendation())
                .overallScore(review.getOverallScore())
                .status(review.getStatus())
                .deadline(review.getDeadline())
                .decision(review.getDecision())
                .editorialAttentionRequired(review.getRequiresEditorialAttention())
                .editorialAttentionReason(
                        review.getEditorialAttentionReason()
                )
                .submittedAt(review.getSubmittedAt())
//                .submitted(review.submit)
                .build();

    }

}
