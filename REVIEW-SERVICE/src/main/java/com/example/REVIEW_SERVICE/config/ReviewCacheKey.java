package com.example.REVIEW_SERVICE.config;

import com.example.REVIEW_SERVICE.service.CurrentUserService;
import com.example.REVIEW_SERVICE.utils.CacheKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("reviewCacheKey")
@RequiredArgsConstructor
public class ReviewCacheKey {

    private final CurrentUserService currentUserService;

    public String assignedReviews(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        return CacheKeys.assignedReviews(
                currentUserService.getCurrentUser().getId(),
                page,
                size,
                sortBy,
                sortDirection
        );
    }

    public String review(Long reviewId) {

        var user = currentUserService.getCurrentUser();

        return CacheKeys.review(
                reviewId,
                user.getId(),
                user.getRole()
        );
    }
}