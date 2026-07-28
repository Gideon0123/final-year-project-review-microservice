package com.example.REVIEW_SERVICE.service;

public interface ReviewReassignmentService {

    void reassignPreviousReviewers(
            Long paperId,
            Integer revisionNumber
    );

}