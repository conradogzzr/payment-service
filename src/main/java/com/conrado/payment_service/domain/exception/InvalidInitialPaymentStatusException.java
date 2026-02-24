package com.conrado.payment_service.domain.exception;

import com.conrado.payment_service.domain.model.PaymentStatus;

public class InvalidInitialPaymentStatusException extends RuntimeException {
    public InvalidInitialPaymentStatusException(PaymentStatus status) {
        super("Invalid initial payment status: " + status);
    }
}
