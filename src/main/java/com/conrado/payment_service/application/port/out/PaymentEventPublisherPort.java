package com.conrado.payment_service.application.port.out;

import com.conrado.payment_service.domain.event.PaymentStatusChangedEvent;

public interface PaymentEventPublisherPort {
    void publish(PaymentStatusChangedEvent event);
}