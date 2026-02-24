package com.conrado.payment_service.api.dto;

import com.conrado.payment_service.domain.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponse(
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