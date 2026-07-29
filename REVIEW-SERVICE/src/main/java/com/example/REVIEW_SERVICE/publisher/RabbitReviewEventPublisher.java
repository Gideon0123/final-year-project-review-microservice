package com.example.REVIEW_SERVICE.publisher;

import com.example.REVIEW_SERVICE.dto.events.*;
import com.example.REVIEW_SERVICE.utils.RabbitMQConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitReviewEventPublisher implements ReviewEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishAssigned(
            ReviewAssignedEvent event
    ) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.REVIEW_EXCHANGE,
                RabbitMQConstants.REVIEW_ASSIGNMENT_ROUTING_KEY,
                event
        );

    }

    @Override
    public void publishAccepted(
            ReviewAcceptedEvent event
    ) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.REVIEW_EXCHANGE,
                RabbitMQConstants.REVIEW_ACCEPTED_ROUTING_KEY,
                event
        );

    }

    @Override
    public void publishDeclined(
            ReviewDeclinedEvent event
    ) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.REVIEW_EXCHANGE,
                RabbitMQConstants.REVIEW_DECLINED_ROUTING_KEY,
                event
        );

    }

    @Override
    public void publishSubmitted(
            ReviewSubmittedEvent event
    ) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.REVIEW_EXCHANGE,
                RabbitMQConstants.REVIEW_SUBMITTED_ROUTING_KEY,
                event
        );

    }

    @Override
    public void publishDecision(
            EditorialDecisionEvent event
    ) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.REVIEW_EXCHANGE,
                RabbitMQConstants.REVIEW_DECISION_ROUTING_KEY,
                event
        );

    }

    @Override
    public void publishRevisionRequested(
            RevisionRequestedEvent event
    ) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.REVIEW_EXCHANGE,
                RabbitMQConstants.REVIEW_REVISION_ROUTING_KEY,
                event
        );

    }

    @Override
    public void publishReviewReminder(ReviewReminderEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.REVIEW_EXCHANGE,
                RabbitMQConstants.REVIEW_REMINDER_ROUTING_KEY,
                event
        );
    }

}