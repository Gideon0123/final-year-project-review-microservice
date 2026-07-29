package com.example.REVIEW_SERVICE.publisher;

import com.example.REVIEW_SERVICE.dto.events.*;
import com.example.REVIEW_SERVICE.utils.RabbitMQConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewEventPublisherImpl implements ReviewEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    private void publish(
            String routingKey,
            Object event
    ) {

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.REVIEW_EXCHANGE,
                routingKey,
                event
        );

    }

    @Override
    public void publishAssigned(
            ReviewAssignedEvent event
    ) {
        publish(
                RabbitMQConstants.REVIEW_ASSIGNMENT_ROUTING_KEY,
                event
        );
    }

    @Override
    public void publishAccepted(
            ReviewAcceptedEvent event
    ) {
        publish(
                RabbitMQConstants.REVIEW_ACCEPTED_ROUTING_KEY,
                event
        );
    }

    @Override
    public void publishDeclined(
            ReviewDeclinedEvent event
    ) {
        publish(
                RabbitMQConstants.REVIEW_DECLINED_ROUTING_KEY,
                event
        );
    }

    @Override
    public void publishSubmitted(
            ReviewSubmittedEvent event
    ) {
        publish(
                RabbitMQConstants.REVIEW_SUBMITTED_ROUTING_KEY,
                event
        );
    }

    @Override
    public void publishDecision(
            EditorialDecisionEvent event
    ) {
        publish(
                RabbitMQConstants.REVIEW_DECISION_ROUTING_KEY,
                event
        );
    }

    @Override
    public void publishRevisionRequested(
            RevisionRequestedEvent event
    ) {
        publish(
                RabbitMQConstants.REVIEW_REVISION_ROUTING_KEY,
                event
        );
    }

    @Override
    public void publishReviewReminder(
            ReviewReminderEvent event
    ) {
        publish(
                RabbitMQConstants.REVIEW_REMINDER_ROUTING_KEY,
                event
        );
    }

}