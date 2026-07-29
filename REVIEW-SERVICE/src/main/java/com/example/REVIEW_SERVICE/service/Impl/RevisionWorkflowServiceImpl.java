package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.NewRevisionRequest;
import com.example.REVIEW_SERVICE.dto.events.RevisionRequestedEvent;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.exception.InvalidReviewStateException;
import com.example.REVIEW_SERVICE.exception.ResourceNotFoundException;
import com.example.REVIEW_SERVICE.publisher.ReviewEventPublisher;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.ReviewReassignmentService;
import com.example.REVIEW_SERVICE.service.RevisionWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RevisionWorkflowServiceImpl implements RevisionWorkflowService {

    private final ReviewRepository reviewRepository;
    private final ReviewReassignmentService reviewReassignmentService;
    private final ReviewEventPublisher reviewEventPublisher;

    @Override
    public void registerRevision(
            NewRevisionRequest request
    ) {
        Review latestReview =
                reviewRepository.findTopByPaperIdOrderByRevisionNumberDesc(
                        request.getPaperId()
                        )
                        .orElseThrow(() -> new ResourceNotFoundException(
                                        "No review history exists."
                                )
                        );

        if (request.getRevisionNumber() <= latestReview.getRevisionNumber()) {

            throw new InvalidReviewStateException(
                    "Revision number must be greater than current revision."
            );

        }

        /*
         * Automatically create
         * next-round review assignments.
         */
        reviewReassignmentService.reassignPreviousReviewers(
                request.getPaperId(),
                request.getRevisionNumber()
        );

        reviewEventPublisher.publishRevisionRequested(
                RevisionRequestedEvent.builder()
                        .paperId(request.getPaperId())
                        .revisionNumber(request.getRevisionNumber())
                        .submittedAt(LocalDateTime.now())
                        .build()
        );
    }
}
