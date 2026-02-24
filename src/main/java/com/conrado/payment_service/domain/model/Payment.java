package com.conrado.payment_service.domain.model;

import com.conrado.payment_service.domain.exception.InvalidInitialPaymentStatusException;
import com.conrado.payment_service.domain.exception.InvalidPaymentStatusTransitionException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Payment {

    private final String id;
    private final String concept;
    private final int productCount;
    private final String payer;
    private final String payee;
    private final BigDecimal totalAmount;

    private PaymentStatus status;

    private final Instant createdAt;
    private Instant updatedAt;

    private Payment(
            String id,
            String concept,
            int productCount,
            String payer,
            String payee,
            BigDecimal totalAmount,
            PaymentStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = Objects.requireNonNull(id);
        this.concept = Objects.requireNonNull(concept);
        this.productCount = productCount;
        this.payer = Objects.requireNonNull(payer);
        this.payee = Objects.requireNonNull(payee);
        this.totalAmount = Objects.requireNonNull(totalAmount);
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static Payment rehydrate(PaymentSnapshot s) {
        // No revalida invariantes “de creación”; asume datos persistidos válidos.
        return new Payment(
                s.id(),
                s.concept(),
                s.productCount(),
                s.payer(),
                s.payee(),
                s.totalAmount(),
                s.status(),
                s.createdAt(),
                s.updatedAt()
        );
    }

    public static Payment create(
            String concept,
            int productCount,
            String payer,
            String payee,
            BigDecimal totalAmount,
            PaymentStatus initialStatus
    ) {
        // Invariantes mínimas del dominio (API también validará con @Valid)
        if (concept == null || concept.isBlank()) throw new IllegalArgumentException("concept must not be blank");
        if (productCount < 1) throw new IllegalArgumentException("productCount must be >= 1");
        if (payer == null || payer.isBlank()) throw new IllegalArgumentException("payer must not be blank");
        if (payee == null || payee.isBlank()) throw new IllegalArgumentException("payee must not be blank");
        if (totalAmount == null || totalAmount.signum() <= 0) throw new IllegalArgumentException("totalAmount must be > 0");

        PaymentStatus status = (initialStatus == null) ? PaymentStatus.PENDING : initialStatus;

        // Regla: estatus inicial permitido
        if (status != PaymentStatus.PENDING) {
            throw new InvalidInitialPaymentStatusException(status);
        }

        Instant now = Instant.now();
        return new Payment(
                UUID.randomUUID().toString(),
                concept.trim(),
                productCount,
                payer.trim(),
                payee.trim(),
                totalAmount,
                status,
                now,
                now
        );
    }

    public void changeStatus(PaymentStatus newStatus) {
        if (!PaymentStatusTransitionPolicy.canTransition(this.status, newStatus)) {
            throw new InvalidPaymentStatusTransitionException(this.status, newStatus);
        }
        this.status = newStatus;
        this.updatedAt = Instant.now();
    }

    public String getId() { return id; }
    public String getConcept() { return concept; }
    public int getProductCount() { return productCount; }
    public String getPayer() { return payer; }
    public String getPayee() { return payee; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public PaymentStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}