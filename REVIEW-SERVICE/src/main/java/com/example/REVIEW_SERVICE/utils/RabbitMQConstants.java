package com.example.REVIEW_SERVICE.utils;

public final class RabbitMQConstants {

    private RabbitMQConstants(){}

    public static final String REVIEW_REMINDER_QUEUE = "review.reminder.queue";

    public static final String REVIEW_REMINDER_ROUTING_KEY = "review.reminder";

    public static final String REVIEW_EXCHANGE = "review.exchange";
}