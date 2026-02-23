package com.conrado.payment_service.infrastructure.mongo;

import com.conrado.payment_service.application.port.out.PaymentRepositoryPort;
import com.conrado.payment_service.domain.model.Payment;
import com.conrado.payment_service.domain.model.PaymentSnapshot;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MongoPaymentRepositoryAdapter implements PaymentRepositoryPort {

    private final SpringDataPaymentRepository repo;

    public MongoPaymentRepositoryAdapter(SpringDataPaymentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentDocument saved = repo.save(toDoc(payment));
        return Payment.rehydrate(toSnapshot(saved));
    }

    @Override
    public Optional<Payment> findById(String id) {
        return repo.findById(id).map(d -> Payment.rehydrate(toSnapshot(d)));
    }

    private static PaymentDocument toDoc(Payment p) {
        PaymentDocument d = new PaymentDocument();
        d.id = p.getId();
        d.concept = p.getConcept();
        d.productCount = p.getProductCount();
        d.payer = p.getPayer();
        d.payee = p.getPayee();
        d.totalAmount = p.getTotalAmount();
        d.status = p.getStatus();
        d.createdAt = p.getCreatedAt();
        d.updatedAt = p.getUpdatedAt();
        return d;
    }

    private static PaymentSnapshot toSnapshot(PaymentDocument d) {
        return new PaymentSnapshot(
                d.id,
                d.concept,
                d.productCount,
                d.payer,
                d.payee,
                d.totalAmount,
                d.status,
                d.createdAt,
                d.updatedAt
        );
    }
}