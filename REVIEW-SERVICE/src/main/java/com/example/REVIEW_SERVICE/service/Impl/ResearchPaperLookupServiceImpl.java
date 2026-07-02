package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.ApiResponse;
import com.example.REVIEW_SERVICE.dto.PaperSummaryResponse;
import com.example.REVIEW_SERVICE.exception.ResearchPaperNotFoundException;
import com.example.REVIEW_SERVICE.feign.ResearchServiceClient;
import com.example.REVIEW_SERVICE.service.ResearchPaperLookupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ResearchPaperLookupServiceImpl implements ResearchPaperLookupService {

    private final ResearchServiceClient researchServiceClient;

    @Override
    public PaperSummaryResponse getPaperSummary(
            Long paperId
    ) {
        ApiResponse<PaperSummaryResponse> response =
                researchServiceClient.getPaperSummary(paperId);

        if (response == null || !response.isSuccess() || response.getData() == null) {

            throw new ResearchPaperNotFoundException("Research paper not found.");

        }

        return response.getData();

    }

}