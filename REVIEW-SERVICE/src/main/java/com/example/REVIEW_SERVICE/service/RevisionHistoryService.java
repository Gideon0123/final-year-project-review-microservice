package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.RevisionHistoryResponse;

import java.util.List;

public interface RevisionHistoryService {

    List<RevisionHistoryResponse> getRevisionHistory(
            Long paperId
    );

}