package com.example.REVIEW_SERVICE.utils;

public final class RabbitMQConstants {

    private RabbitMQConstants() {
    }

    /*
     * Exchanges
     */
    public static final String REVIEW_EXCHANGE =
            "review.exchange";

    public static final String REVIEW_DLX =
            "review.dlx";

    /*
     * Assignment
     */
    public static final String REVIEW_ASSIGNMENT_QUEUE =
            "review.assignment.queue";

    public static final String REVIEW_ASSIGNMENT_ROUTING_KEY =
            "review.assignment";

    /*
     * Invitation Accepted
     */
    public static final String REVIEW_ACCEPTED_QUEUE =
            "review.accepted.queue";

    public static final String REVIEW_ACCEPTED_ROUTING_KEY =
            "review.accepted";

    /*
     * Invitation Declined
     */
    public static final String REVIEW_DECLINED_QUEUE =
            "review.declined.queue";

    public static final String REVIEW_DECLINED_ROUTING_KEY =
            "review.declined";

    /*
     * Submission
     */
    public static final String REVIEW_SUBMITTED_QUEUE =
            "review.submitted.queue";

    public static final String REVIEW_SUBMITTED_ROUTING_KEY =
            "review.submitted";

    /*
     * Decision
     */
    public static final String REVIEW_DECISION_QUEUE =
            "review.decision.queue";

    public static final String REVIEW_DECISION_ROUTING_KEY =
            "review.decision";

    /*
     * Reminder
     */
    public static final String REVIEW_REMINDER_QUEUE =
            "review.reminder.queue";

    public static final String REVIEW_REMINDER_ROUTING_KEY =
            "review.reminder";

    /*
     * Revision Requested
     */
    public static final String REVIEW_REVISION_QUEUE =
            "review.revision.queue";

    public static final String REVIEW_REVISION_ROUTING_KEY =
            "review.revision";

    /*
     * Review Accepted
     */
    public static final String REVIEW_ACCEPTED_PAPER_QUEUE =
            "review.paper.accepted.queue";

    public static final String REVIEW_ACCEPTED_PAPER_ROUTING_KEY =
            "review.paper.accepted";

    public static final String REVIEW_ESCALATION_QUEUE =
            "review.escalation.queue";

    public static final String REVIEW_ESCALATION_ROUTING_KEY =
            "review.escalation";

    public static final String REVIEW_ESCALATION_EXCHANGE =
            REVIEW_EXCHANGE;

}