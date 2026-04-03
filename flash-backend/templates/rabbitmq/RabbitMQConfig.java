package ru.lomov.flashbackend.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ configuration for Flash-Spring microservices.
 * Provides message broker integration for async communication.
 */
@Configuration
public class RabbitMQConfig {

    @Value("${flash.rabbitmq.exchanges.main.name:flash.main.exchange}")
    private String mainExchangeName;

    @Value("${flash.rabbitmq.exchanges.dead-letter.name:flash.dl.exchange}")
    private String dlExchangeName;

    @Value("${flash.rabbitmq.queues.notifications.name:flash.notifications}")
    private String notificationsQueue;

    @Value("${flash.rabbitmq.queues.user-events.name:flash.user-events}")
    private String userEventsQueue;

    @Value("${flash.rabbitmq.queues.post-events.name:flash.post-events}")
    private String postEventsQueue;

    /**
     * Main topic exchange for routing messages
     */
    @Bean
    public TopicExchange mainExchange() {
        return new TopicExchange(mainExchangeName, true, false);
    }

    /**
     * Dead letter exchange for failed messages
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(dlExchangeName, true, false);
    }

    /**
     * Notifications queue for sending notifications
     */
    @Bean
    public Queue notificationsQueue() {
        return QueueBuilder.durable(notificationsQueue)
            .withArgument("x-dead-letter-exchange", dlExchangeName)
            .withArgument("x-dead-letter-routing-key", "notification.dlq")
            .build();
    }

    /**
     * User events queue for user-related events
     */
    @Bean
    public Queue userEventsQueue() {
        return QueueBuilder.durable(userEventsQueue)
            .withArgument("x-dead-letter-exchange", dlExchangeName)
            .withArgument("x-dead-letter-routing-key", "user.dlq")
            .build();
    }

    /**
     * Post events queue for post-related events
     */
    @Bean
    public Queue postEventsQueue() {
        return QueueBuilder.durable(postEventsQueue)
            .withArgument("x-dead-letter-exchange", dlExchangeName)
            .withArgument("x-dead-letter-routing-key", "post.dlq")
            .build();
    }

    /**
     * Bind notifications queue to main exchange
     */
    @Bean
    public Binding notificationsBinding(Queue notificationsQueue, TopicExchange mainExchange) {
        return BindingBuilder.bind(notificationsQueue)
            .to(mainExchange)
            .with("notification.#");
    }

    /**
     * Bind user events queue to main exchange
     */
    @Bean
    public Binding userEventsBinding(Queue userEventsQueue, TopicExchange mainExchange) {
        return BindingBuilder.bind(userEventsQueue)
            .to(mainExchange)
            .with("user.#");
    }

    /**
     * Bind post events queue to main exchange
     */
    @Bean
    public Binding postEventsBinding(Queue postEventsQueue, TopicExchange mainExchange) {
        return BindingBuilder.bind(postEventsQueue)
            .to(mainExchange)
            .with("post.#");
    }

    /**
     * JSON message converter for serialization/deserialization
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate for sending messages
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
