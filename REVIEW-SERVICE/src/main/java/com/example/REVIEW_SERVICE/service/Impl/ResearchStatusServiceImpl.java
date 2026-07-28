package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.ResearchStatusUpdateRequest;
import com.example.REVIEW_SERVICE.enums.EditorialDecision;
import com.example.REVIEW_SERVICE.enums.ResearchStatus;
import com.example.REVIEW_SERVICE.feign.ResearchServiceClient;
import com.example.REVIEW_SERVICE.service.ResearchStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResearchStatusServiceImpl implements ResearchStatusService {

    private final ResearchServiceClient researchServiceClient;

    @Override
    public void updatePaperStatus(
            Long paperId,
            EditorialDecision decision
    ) {
        ResearchStatus status =
                switch (decision) {
                    case ACCEPT -> ResearchStatus.ACCEPTED;

                    case REJECT -> ResearchStatus.REJECTED;

                    case MINOR_REVISION, MAJOR_REVISION ->
                            ResearchStatus.REVISION_REQUESTED;
                };

        researchServiceClient.updateStatus(
                paperId,
                ResearchStatusUpdateRequest.builder()
                        .status(status)
                        .build()
        );

    }

}