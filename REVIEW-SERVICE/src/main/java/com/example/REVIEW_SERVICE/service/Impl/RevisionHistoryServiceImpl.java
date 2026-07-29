package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.ReviewSummaryResponse;
import com.example.REVIEW_SERVICE.dto.RevisionHistoryResponse;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.mapper.ReviewMapper;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.RevisionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RevisionHistoryServiceImpl implements RevisionHistoryService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RevisionHistoryResponse> getRevisionHistory(
            Long paperId
    ) {
        List<Review> reviews =
                reviewRepository.findByPaperIdOrderByRevisionNumberAscReviewRoundAsc(
                        paperId
                );

        Map<Integer, List<Review>> grouped = reviews.stream()
                .collect(
                        Collectors.groupingBy(
                                Review::getRevisionNumber,
                                LinkedHashMap::new,
                                Collectors.toList()
                        )
                );

        List<RevisionHistoryResponse> response = new ArrayList<>();

        for (Map.Entry<Integer, List<Review>> entry : grouped.entrySet()) {

            List<Review> revisionReviews = entry.getValue();

            Review latest = revisionReviews.getFirst();

            response.add(
                    RevisionHistoryResponse.builder()
                            .revisionNumber(entry.getKey())
                            .reviewRound(latest.getReviewRound())
                            .editorialDecision(latest.getDecision())
                            .reviews(
                                    revisionReviews.stream()
                                            .map(review ->
                                                    ReviewSummaryResponse.builder()
                                                            .id(review.getId())
                                                            .paperId(review.getPaperId())
                                                            .reviewerId(review.getReviewerId())
                                                            .status(review.getStatus())
                                                            .deadline(review.getDeadline())
                                                            .revisionNumber(review.getRevisionNumber())
                                                            .build()

                                            )
                                            .toList()

                            )
                            .build()

            );

        }

        return response;

    }
}
