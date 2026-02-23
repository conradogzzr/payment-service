package com.conrado.payment_service.infrastructure.rabbit;

import com.conrado.payment_service.application.port.out.PaymentEventPublisherPort;
import com.conrado.payment_service.domain.event.PaymentStatusChangedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitPaymentEventPublisherAdapter implements PaymentEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public RabbitPaymentEventPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(PaymentStatusChangedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY_STATUS_CHANGED,
                event
        );
    }
}