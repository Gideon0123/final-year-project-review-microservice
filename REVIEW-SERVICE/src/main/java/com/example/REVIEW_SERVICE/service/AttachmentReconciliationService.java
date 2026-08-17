package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.repository.ReviewAttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void removeOrphanedObjects() {

        List<String> databaseKeys = reviewAttachmentRepository
                .findAllObjectKeys();

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