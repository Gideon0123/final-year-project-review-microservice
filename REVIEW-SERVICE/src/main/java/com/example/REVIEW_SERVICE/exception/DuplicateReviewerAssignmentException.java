package com.example.REVIEW_SERVICE.exception;

public class DuplicateReviewerAssignmentException extends RuntimeException {
    public DuplicateReviewerAssignmentException(String message) {
        super(message);
    }
}
