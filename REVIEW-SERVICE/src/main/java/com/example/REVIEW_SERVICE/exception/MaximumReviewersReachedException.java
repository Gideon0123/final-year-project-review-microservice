package com.example.REVIEW_SERVICE.exception;

public class MaximumReviewersReachedException extends RuntimeException {
    public MaximumReviewersReachedException(String message) {
        super(message);
    }
}
