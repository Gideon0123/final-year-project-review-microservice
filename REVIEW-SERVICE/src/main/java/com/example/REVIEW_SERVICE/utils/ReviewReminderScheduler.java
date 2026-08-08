package com.example.REVIEW_SERVICE.utils;

import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
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

        /*
         * Send reminders for reviews whose deadline is
         * within the next 3 days.
         */
        LocalDateTime reminderThreshold =
                now.plusDays(3);

        /*
         * Prevent the same review from receiving a reminder
         * too frequently.
         *
         * Here we allow one reminder every 24 hours.
         */
        LocalDateTime reminderCutoff =
                now.minusDays(1);

        List<ReviewStatus> activeStatuses = List.of(
                ReviewStatus.PENDING_INVITATION,
                ReviewStatus.INVITATION_ACCEPTED,
                ReviewStatus.IN_PROGRESS
        );

        List<Review> reviews =
                reviewRepository.findReviewsNeedingReminder(
                        activeStatuses,
                        now,
                        reminderThreshold,
                        reminderCutoff
                );

        for (Review review : reviews) {

            reminderService.sendDeadlineReminder(review);

            review.setLastReminderSentAt(now);
        }

        reviewRepository.saveAll(reviews);

        log.info(
                "{} review deadline reminder(s) sent.",
                reviews.size()
        );
    }

    @Scheduled(cron = "0 0 9 * * *")
    @Transactional
    public void sendEscalationReminders() {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime escalationCutoff = now.minusHours(23);

        List<ReviewStatus> escalationStatuses = List.of(
                ReviewStatus.PENDING_INVITATION,
                ReviewStatus.INVITATION_ACCEPTED,
                ReviewStatus.IN_PROGRESS
        );

        List<Review> reviews =
                reviewRepository.findReviewsNeedingEscalation(
                        escalationStatuses,
                        now,
                        escalationCutoff
                );

        for (Review review : reviews) {

            try {
                reminderService.sendEscalationReminder(
                        review
                );

                review.setLastEscalationSentAt(now);

            } catch (Exception exception) {

                log.error(
                        "Failed to send escalation for review {}",
                        review.getId(),
                        exception
                );
            }
        }

        reviewRepository.saveAll(reviews);

        log.info(
                "{} escalation reminder(s) processed.",
                reviews.size()
        );
    }
}