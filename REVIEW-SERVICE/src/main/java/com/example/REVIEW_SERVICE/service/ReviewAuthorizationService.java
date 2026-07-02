package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.Review;

public interface ReviewAuthorizationService {

    void verifyReviewer(Review review);
    void verifyEditor();

}