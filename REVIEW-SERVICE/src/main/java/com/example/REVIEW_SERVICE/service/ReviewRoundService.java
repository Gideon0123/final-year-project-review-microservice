package com.example.REVIEW_SERVICE.service;

public interface ReviewRoundService {

    Integer determineNextRound(
            Long paperId
    );

}