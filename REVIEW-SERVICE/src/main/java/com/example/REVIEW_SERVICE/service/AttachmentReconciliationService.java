package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.ReviewAttachment;
import com.example.REVIEW_SERVICE.repository.ReviewAttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentReconciliationService {

    private final ReviewAttachmentRepository reviewAttachmentRepository;
    private final StorageService storageService;

    @Transactional
    public void removeBrokenMetadata() {

        List<ReviewAttachment> attachments = reviewAttachmentRepository.findAll();

        List<ReviewAttachment> broken = new ArrayList<>();

        reviewAttachmentRepository.deleteAll(broken);

//        for (ReviewAttachment attachment : attachments) {
//            boolean exists = storageService.exists(attachment.getObjectKey());
//            if (!exists) {
//                log.warn(
//                        "Broken metadata detected. attachmentId={}",
//                        attachment.getId()
//                );
//
//                reviewAttachmentRepository.delete(attachment);
//            }
//        }
    }

    @Transactional
    public void removeOrphanedObjects() {

        List<String> databaseKeys = reviewAttachmentRepository.findAllObjectKeys();

        Set<String> dbKeySet = new HashSet<>(databaseKeys);

        List<String> storageKeys = storageService.listObjects("reviews/");

        for (String objectKey : storageKeys) {
            if (!dbKeySet.contains(objectKey)) {
                log.warn(
                        "Orphaned object detected. objectKey={}",
                        objectKey
                );

               storageService.delete(objectKey);
            }
        }
    }
}