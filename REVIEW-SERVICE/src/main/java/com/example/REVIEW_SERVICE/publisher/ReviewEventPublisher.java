package com.example.REVIEW_SERVICE.publisher;

import com.example.REVIEW_SERVICE.dto.events.*;

public interface ReviewEventPublisher {

    void publishAssigned(ReviewAssignedEvent event);

    void publishAccepted(ReviewAcceptedEvent event);

    void publishDeclined(ReviewDeclinedEvent event);

    void publishSubmitted(ReviewSubmittedEvent event);

    void publishDecision(EditorialDecisionEvent event);

    void publishRevisionRequested(RevisionRequestedEvent event);

    void publishReviewReminder(ReviewReminderEvent event);

}