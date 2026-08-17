package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.ReviewAttachment;
import com.example.REVIEW_SERVICE.repository.ReviewAttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentReconciliationService {

    private final ReviewAttachmentRepository reviewAttachmentRepository;
    private final StorageService storageService;

    @Transactional
    public void removeBrokenMetadata() {

        List<ReviewAttachment> attachments = reviewAttachmentRepository.findAll();

        for (ReviewAttachment attachment : attachments) {
            boolean exists = storageService.exists(attachment.getObjectKey());

            if (!exists) {
                log.warn(
                        "Attachment metadata exists but object missing. attachmentId={}",
                        attachment.getId()
                );

                reviewAttachmentRepository.delete(attachment);
            }
        }
    }
}