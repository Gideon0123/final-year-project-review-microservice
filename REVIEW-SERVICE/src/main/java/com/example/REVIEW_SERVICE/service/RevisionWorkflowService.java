package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.NewRevisionRequest;

public interface RevisionWorkflowService {

    void registerRevision(NewRevisionRequest request);

}
