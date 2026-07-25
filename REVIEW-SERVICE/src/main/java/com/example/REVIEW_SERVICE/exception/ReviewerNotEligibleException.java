package com.example.REVIEW_SERVICE.exception;

public class ReviewerNotEligibleException extends RuntimeException {
    public ReviewerNotEligibleException(String message) {
        super(message);
    }
}
