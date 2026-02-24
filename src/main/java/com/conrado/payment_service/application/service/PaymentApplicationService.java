package com.conrado.payment_service.application.service;

import com.conrado.payment_service.application.port.out.PaymentEventPublisherPort;
import com.conrado.payment_service.application.port.out.PaymentRepositoryPort;
import com.conrado.payment_service.domain.event.PaymentStatusChangedEvent;
import com.conrado.payment_service.domain.exception.PaymentNotFoundException;
import com.conrado.payment_service.domain.model.Payment;
import com.conrado.payment_service.domain.model.PaymentStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentApplicationService {

    private final PaymentRepositoryPort paymentRepository;
    private final PaymentEventPublisherPort eventPublisher;

    public PaymentApplicationService(PaymentRepositoryPort paymentRepository, PaymentEventPublisherPort eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    public Payment createPayment(
            String concept,
            int productCount,
            String payer,
            String payee,
            java.math.BigDecimal totalAmount,
            PaymentStatus initialStatus
    ) {
        Payment payment = Payment.create(concept, productCount, payer, payee, totalAmount, initialStatus);
        return paymentRepository.save(payment);
    }

    public PaymentStatus getPaymentStatus(String paymentId) {
        return paymentRepository.findById(paymentId)
                .map(Payment::getStatus)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }

    public Payment updatePaymentStatus(String paymentId, PaymentStatus newStatus) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        PaymentStatus oldStatus = payment.getStatus();
        payment.changeStatus(newStatus);

        Payment saved = paymentRepository.save(payment);

        eventPublisher.publish(new PaymentStatusChangedEvent(
                saved.getId(),
                oldStatus,
                saved.getStatus(),
                Instant.now()
        ));

        return saved;
    }
}