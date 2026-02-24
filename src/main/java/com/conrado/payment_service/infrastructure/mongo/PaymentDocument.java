package com.conrado.payment_service.infrastructure.mongo;

import com.conrado.payment_service.domain.model.PaymentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document("payments")
public class PaymentDocument {
    @Id
    public String id;

    public String concept;
    public int productCount;
    public String payer;
    public String payee;
    public BigDecimal totalAmount;
    public PaymentStatus status;

    public Instant createdAt;
    public Instant updatedAt;
}