package com.example.REVIEW_SERVICE.controller;

import com.example.REVIEW_SERVICE.dto.NewRevisionRequest;
import com.example.REVIEW_SERVICE.service.RevisionWorkflowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/revisions")
@RequiredArgsConstructor
public class RevisionWorkflowController {

    private final RevisionWorkflowService revisionWorkflowService;

    @PostMapping
    public ResponseEntity<Void> registerRevision(
            @Valid @RequestBody NewRevisionRequest request
    ) {

        revisionWorkflowService.registerRevision(request);

        return ResponseEntity.ok().build();
    }

}