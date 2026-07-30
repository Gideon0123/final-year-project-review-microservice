package com.example.REVIEW_SERVICE.service.Impl;

import com.example.REVIEW_SERVICE.dto.ReviewerSummaryResponse;
import com.example.REVIEW_SERVICE.dto.events.ReviewReminderEvent;
import com.example.REVIEW_SERVICE.entity.Review;
import com.example.REVIEW_SERVICE.service.AuthLookupService;
import com.example.REVIEW_SERVICE.service.ReminderService;
import com.example.REVIEW_SERVICE.utils.RabbitMQConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final RabbitTemplate rabbitTemplate;
    private final AuthLookupService authLookupService;

    @Override
    public void sendDeadlineReminder(
            Review review
    ) {

        ReviewerSummaryResponse reviewer = authLookupService.getReviewer(
                review.getReviewerId()
        );

        ReviewReminderEvent event = ReviewReminderEvent.builder()
                .reviewId(review.getId())
                .reviewerId(review.getReviewerId())
                .reviewerEmail(reviewer.getEmail())
                .paperId(review.getPaperId())
                .deadline(review.getDeadline())
                .build();

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.REVIEW_EXCHANGE,
                RabbitMQConstants.REVIEW_REMINDER_ROUTING_KEY,
                event
        );

    }

}