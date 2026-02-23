package com.conrado.payment_service.domain.model;

import com.conrado.payment_service.infrastructure.mongo.PaymentDocument;

import java.time.Instant;

public final class PaymentRehydrator {

    private PaymentRehydrator(){}

    public static Payment fromDocument(PaymentDocument d) {
        throw new UnsupportedOperationException("Pending design tweak");
    }
}
