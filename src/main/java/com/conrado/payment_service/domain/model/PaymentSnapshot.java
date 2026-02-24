package com.conrado.payment_service.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentSnapshot(
        String id,
        String concept,
        int productCount,
        String payer,
        String payee,
        BigDecimal totalAmount,
        PaymentStatus status,
        Instant createdAt,
        Instant updatedAt
) { }
