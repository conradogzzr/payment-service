package com.conrado.payment_service.api.dto;

import com.conrado.payment_service.domain.model.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatePaymentStatusRequest(
        @NotNull PaymentStatus status
) { }