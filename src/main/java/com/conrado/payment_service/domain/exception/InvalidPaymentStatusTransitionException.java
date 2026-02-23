package com.conrado.payment_service.domain.exception;

import com.conrado.payment_service.domain.model.PaymentStatus;

public class InvalidPaymentStatusTransitionException extends RuntimeException {

    public InvalidPaymentStatusTransitionException(PaymentStatus from, PaymentStatus to) {
        super("Invalid payment status transition: " + from + " -> " + to);
    }
}
