package com.example.REVIEW_SERVICE.enums;

public enum IdempotencyState {
    NOT_FOUND,
    PROCESSING,
    COMPLETED,
    FINGERPRINT_MISMATCH
}