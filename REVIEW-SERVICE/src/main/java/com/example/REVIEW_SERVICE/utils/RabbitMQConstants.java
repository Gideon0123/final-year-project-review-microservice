package com.example.REVIEW_SERVICE.utils;

public final class RabbitMQConstants {

    private RabbitMQConstants(){}

    /*
     * Exchanges
     */
    public static final String REVIEW_EXCHANGE =
            "review.exchange";

    /*
     * Queues
     */
    public static final String REVIEW_REMINDER_QUEUE =
            "review.reminder.queue";

    public static final String REVIEW_EVENT_QUEUE =
            "review.event.queue";

    /*
     * Routing Keys
     */
    public static final String REVIEW_REMINDER_ROUTING_KEY =
            "review.reminder";

    public static final String REVIEW_ASSIGNED_ROUTING_KEY =
            "review.assigned";

    public static final String REVIEW_ACCEPTED_ROUTING_KEY =
            "review.accepted";

    public static final String REVIEW_DECLINED_ROUTING_KEY =
            "review.declined";

    public static final String REVIEW_SUBMITTED_ROUTING_KEY =
            "review.submitted";

    public static final String REVIEW_DECISION_ROUTING_KEY =
            "review.decision";

    public static final String REVIEW_REVISION_ROUTING_KEY =
            "review.revision";
}