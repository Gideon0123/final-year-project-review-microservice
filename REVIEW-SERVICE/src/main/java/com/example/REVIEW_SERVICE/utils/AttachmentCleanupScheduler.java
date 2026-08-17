package com.example.REVIEW_SERVICE.utils;

import com.example.REVIEW_SERVICE.service.AttachmentReconciliationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AttachmentCleanupScheduler {

    private final AttachmentReconciliationService reconciliationService;

    @Scheduled(cron = "0 0 2 * * *")
    public void reconcileAttachments() {

        log.info("Starting attachment reconciliation");

//        reconciliationService.removeBrokenMetadata();
        reconciliationService.removeOrphanedObjects();

        log.info("Attachment reconciliation completed");
    }
}