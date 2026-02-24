package com.conrado.payment_service.infrastructure.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "payments.exchange";
    public static final String QUEUE_STATUS_CHANGED = "payments.status-changed.queue";
    public static final String ROUTING_KEY_STATUS_CHANGED = "payments.status-changed";

    @Bean
    public TopicExchange paymentsExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue paymentStatusChangedQueue() {
        return QueueBuilder.durable(QUEUE_STATUS_CHANGED).build();
    }

    @Bean
    public Binding paymentStatusChangedBinding(TopicExchange paymentsExchange, Queue paymentStatusChangedQueue) {
        return BindingBuilder
                .bind(paymentStatusChangedQueue)
                .to(paymentsExchange)
                .with(ROUTING_KEY_STATUS_CHANGED);
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jacksonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jacksonMessageConverter);
        return template;
    }
}