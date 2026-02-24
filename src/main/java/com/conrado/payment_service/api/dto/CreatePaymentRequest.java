package com.conrado.payment_service.api.dto;

import com.conrado.payment_service.domain.model.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatePaymentRequest(
        @NotBlank String concept,
        @Min(1) int productCount,
        @NotBlank String payer,
        @NotBlank String payee,
        @NotNull @DecimalMin(value = "0.01") BigDecimal totalAmount,
        PaymentStatus status
) { }
