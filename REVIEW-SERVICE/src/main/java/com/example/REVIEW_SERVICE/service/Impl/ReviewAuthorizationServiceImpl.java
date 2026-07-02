package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.entity.CurrentUser;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.exception.ReviewAuthorizationException;
import com.example.REVIEW_SERVICE.service.CurrentUserService;
import com.example.REVIEW_SERVICE.service.ReviewAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewAuthorizationServiceImpl implements ReviewAuthorizationService {

    private final CurrentUserService currentUserService;

    @Override
    public void verifyReviewer(
            Review review
    ) {
        CurrentUser currentUser = currentUserService.getCurrentUser();

        if (!"REVIEWER".equalsIgnoreCase(currentUser.getRole())) {
            throw new ReviewAuthorizationException(
                    "Only reviewers may perform this operation."
            );

        }

        if (!review.getReviewerId().equals(currentUser.getId())) {
            throw new ReviewAuthorizationException(
                    "You are not assigned to this review."
            );
        }

    }

    @Override
    public void verifyEditor() {

        CurrentUser currentUser = currentUserService.getCurrentUser();

        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            throw new ReviewAuthorizationException(
                    "Only editors may perform this operation."
            );

        }

    }

}