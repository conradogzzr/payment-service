package com.conrado.payment_service.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataPaymentRepository extends MongoRepository<PaymentDocument, String> { }