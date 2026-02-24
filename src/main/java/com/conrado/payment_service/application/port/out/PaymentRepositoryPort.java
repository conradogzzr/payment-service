package com.conrado.payment_service.application.port.out;

import com.conrado.payment_service.domain.model.Payment;
import java.util.Optional;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
    Optional<Payment> findById(String id);
}