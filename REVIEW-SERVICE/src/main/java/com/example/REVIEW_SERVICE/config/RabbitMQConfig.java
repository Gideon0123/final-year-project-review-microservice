package com.example.REVIEW_SERVICE.config;

import com.example.REVIEW_SERVICE.utils.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /*
     * ============================================================
     * MAIN REVIEW EXCHANGE
     * ============================================================
     */

    @Bean
    public TopicExchange reviewExchange() {

        return ExchangeBuilder
                .topicExchange(RabbitMQConstants.REVIEW_EXCHANGE)
                .durable(true)
                .build();
    }


    /*
     * ============================================================
     * DEAD LETTER EXCHANGE
     * ============================================================
     */

    @Bean
    public DirectExchange reviewDeadLetterExchange() {

        return ExchangeBuilder
                .directExchange(RabbitMQConstants.REVIEW_DLX)
                .durable(true)
                .build();
    }


    /*
     * ============================================================
     * COMMON REVIEW QUEUE BUILDER
     * ============================================================
     *
     * Every review queue uses exactly the same DLQ configuration.
     */

    private Queue buildReviewQueue(String queueName) {

        return QueueBuilder
                .durable(queueName)

                .withArgument(
                        "x-dead-letter-exchange",
                        RabbitMQConstants.REVIEW_DLX
                )

                .withArgument(
                        "x-dead-letter-routing-key",
                        RabbitMQConstants.REVIEW_DL_ROUTING_KEY
                )

                .build();
    }


    /*
     * ============================================================
     * REVIEW QUEUES
     * ============================================================
     */

    @Bean
    public Queue assignmentQueue() {

        return buildReviewQueue(
                RabbitMQConstants.REVIEW_ASSIGNMENT_QUEUE
        );
    }


    @Bean
    public Queue acceptedQueue() {

        return buildReviewQueue(
                RabbitMQConstants.REVIEW_ACCEPTED_QUEUE
        );
    }


    @Bean
    public Queue declinedQueue() {

        return buildReviewQueue(
                RabbitMQConstants.REVIEW_DECLINED_QUEUE
        );
    }


    @Bean
    public Queue submittedQueue() {

        return buildReviewQueue(
                RabbitMQConstants.REVIEW_SUBMITTED_QUEUE
        );
    }


    @Bean
    public Queue decisionQueue() {

        return buildReviewQueue(
                RabbitMQConstants.REVIEW_DECISION_QUEUE
        );
    }


    @Bean
    public Queue reminderQueue() {

        return buildReviewQueue(
                RabbitMQConstants.REVIEW_REMINDER_QUEUE
        );
    }


    @Bean
    public Queue revisionQueue() {

        return buildReviewQueue(
                RabbitMQConstants.REVIEW_REVISION_QUEUE
        );
    }


    @Bean
    public Queue acceptedPaperQueue() {

        return buildReviewQueue(
                RabbitMQConstants.REVIEW_ACCEPTED_PAPER_QUEUE
        );
    }


    @Bean
    public Queue escalationQueue() {

        return buildReviewQueue(
                RabbitMQConstants.REVIEW_ESCALATION_QUEUE
        );
    }


    /*
     * ============================================================
     * REVIEW QUEUE BINDINGS
     * ============================================================
     */

    @Bean
    public Binding assignmentBinding(
            Queue assignmentQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(assignmentQueue)
                .to(reviewExchange)
                .with(
                        RabbitMQConstants.REVIEW_ASSIGNMENT_ROUTING_KEY
                );
    }


    @Bean
    public Binding acceptedBinding(
            Queue acceptedQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(acceptedQueue)
                .to(reviewExchange)
                .with(
                        RabbitMQConstants.REVIEW_ACCEPTED_ROUTING_KEY
                );
    }


    @Bean
    public Binding declinedBinding(
            Queue declinedQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(declinedQueue)
                .to(reviewExchange)
                .with(
                        RabbitMQConstants.REVIEW_DECLINED_ROUTING_KEY
                );
    }


    @Bean
    public Binding submittedBinding(
            Queue submittedQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(submittedQueue)
                .to(reviewExchange)
                .with(
                        RabbitMQConstants.REVIEW_SUBMITTED_ROUTING_KEY
                );
    }


    @Bean
    public Binding decisionBinding(
            Queue decisionQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(decisionQueue)
                .to(reviewExchange)
                .with(
                        RabbitMQConstants.REVIEW_DECISION_ROUTING_KEY
                );
    }


    @Bean
    public Binding reminderBinding(
            Queue reminderQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(reminderQueue)
                .to(reviewExchange)
                .with(
                        RabbitMQConstants.REVIEW_REMINDER_ROUTING_KEY
                );
    }


    @Bean
    public Binding revisionBinding(
            Queue revisionQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(revisionQueue)
                .to(reviewExchange)
                .with(
                        RabbitMQConstants.REVIEW_REVISION_ROUTING_KEY
                );
    }


    @Bean
    public Binding acceptedPaperBinding(
            Queue acceptedPaperQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(acceptedPaperQueue)
                .to(reviewExchange)
                .with(
                        RabbitMQConstants.REVIEW_ACCEPTED_PAPER_ROUTING_KEY
                );
    }


    @Bean
    public Binding escalationBinding(
            Queue escalationQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(escalationQueue)
                .to(reviewExchange)
                .with(
                        RabbitMQConstants.REVIEW_ESCALATION_ROUTING_KEY
                );
    }


    /*
     * ============================================================
     * REVIEW DEAD LETTER QUEUE
     * ============================================================
     */

    @Bean
    public Queue reviewDeadLetterQueue() {

        return QueueBuilder
                .durable(
                        RabbitMQConstants.REVIEW_DLQ
                )
                .build();
    }


    /*
     * ============================================================
     * DEAD LETTER BINDING
     * ============================================================
     */

    @Bean
    public Binding reviewDeadLetterBinding(
            Queue reviewDeadLetterQueue,
            DirectExchange reviewDeadLetterExchange
    ) {

        return BindingBuilder
                .bind(reviewDeadLetterQueue)
                .to(reviewDeadLetterExchange)
                .with(
                        RabbitMQConstants.REVIEW_DL_ROUTING_KEY
                );
    }


    /*
     * ============================================================
     * MESSAGE CONVERTER
     * ============================================================
     */

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {

        return new Jackson2JsonMessageConverter();
    }


    /*
     * ============================================================
     * RABBIT TEMPLATE
     * ============================================================
     */

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter converter
    ) {

        RabbitTemplate rabbitTemplate =
                new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(converter);

        return rabbitTemplate;
    }
}