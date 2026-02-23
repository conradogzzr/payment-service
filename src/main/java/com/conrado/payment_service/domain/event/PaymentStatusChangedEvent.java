package com.conrado.payment_service.domain.event;

import com.conrado.payment_service.domain.model.PaymentStatus;
import java.time.Instant;

public record PaymentStatusChangedEvent(
        String paymentId,
        PaymentStatus oldStatus,
        PaymentStatus newStatus,
        Instant occurredAt
) { }
