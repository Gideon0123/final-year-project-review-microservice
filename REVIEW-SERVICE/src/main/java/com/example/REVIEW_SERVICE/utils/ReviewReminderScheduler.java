package com.example.REVIEW_SERVICE.utils;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.repository.ReviewRepository;
import com.example.REVIEW_SERVICE.service.ReminderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewReminderScheduler {

    private final ReviewRepository reviewRepository;
    private final ReminderService reminderService;

    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void sendDeadlineReminders() {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime reminderThreshold = now.plusDays(3);

        LocalDateTime reminderCutoff = now.minusHours(23);

        List<Review> reviews =
                reviewRepository.findReviewsNeedingReminder(
                        now,
                        reminderThreshold,
                        reminderCutoff
                );

        for (Review review : reviews) {
            try {
                reminderService.sendDeadlineReminder(review);

                review.setLastReminderSentAt(now);

            } catch (Exception exception) {
                log.error(
                        "Failed to send reminder for review {}",
                        review.getId(),
                        exception
                );

            }
        }

        reviewRepository.saveAll(reviews);
        log.info(
                "Deadline reminder scheduler completed. {} reminder(s) processed.",
                reviews.size()
        );
    }
}