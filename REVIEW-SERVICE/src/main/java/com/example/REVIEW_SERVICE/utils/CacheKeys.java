package com.example.REVIEW_SERVICE.utils;

public final class CacheKeys {

    private CacheKeys() {
    }

    /*
     * ============================================================
     * REVIEW KEYS
     * ============================================================
     */

    public static String review(Long reviewId) {
        return "review:" + reviewId;
    }

    public static String assignedReviews(
            Long reviewerId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        return "reviewer:" +
                reviewerId +
                ":page:" +
                page +
                ":size:" +
                size +
                ":sortBy:" +
                sortBy +
                ":sortDirection:" +
                sortDirection;
    }

    public static String paperReviews(
            Long paperId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        return "paper:" +
                paperId +
                ":page:" +
                page +
                ":size:" +
                size +
                ":sortBy:" +
                sortBy +
                ":sortDirection:" +
                sortDirection;
    }


    /*
     * ============================================================
     * REVISION HISTORY KEYS
     * ============================================================
     */

    public static String revisionHistory(Long paperId) {
        return "paper:" + paperId;
    }


    /*
     * ============================================================
     * REVIEW ATTACHMENT KEYS
     * ============================================================
     */

    public static String attachment(
            Long reviewId,
            Long attachmentId
    ) {
        return "review:" +
                reviewId +
                ":attachment:" +
                attachmentId;
    }

    public static String attachmentExists(
            Long reviewId,
            Long attachmentId
    ) {
        return "review:" +
                reviewId +
                ":attachment:" +
                attachmentId +
                ":exists";
    }

}