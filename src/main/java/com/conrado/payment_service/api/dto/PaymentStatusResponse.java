package com.conrado.payment_service.api.dto;

import com.conrado.payment_service.domain.model.PaymentStatus;

public record PaymentStatusResponse(
        String id,
        PaymentStatus status
) { }