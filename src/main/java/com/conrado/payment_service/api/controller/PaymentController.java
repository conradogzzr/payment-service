package com.conrado.payment_service.api.controller;

import com.conrado.payment_service.api.dto.CreatePaymentRequest;
import com.conrado.payment_service.api.dto.PaymentResponse;
import com.conrado.payment_service.api.dto.PaymentStatusResponse;
import com.conrado.payment_service.api.dto.UpdatePaymentStatusRequest;
import com.conrado.payment_service.application.service.PaymentApplicationService;
import com.conrado.payment_service.domain.model.Payment;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentApplicationService paymentService;

    public PaymentController(PaymentApplicationService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse create(@Valid @RequestBody CreatePaymentRequest request) {
        Payment created = paymentService.createPayment(
                request.concept(),
                request.productCount(),
                request.payer(),
                request.payee(),
                request.totalAmount(),
                request.status()
        );
        return toResponse(created);
    }

    @GetMapping("/{id}/status")
    public PaymentStatusResponse getStatus(@PathVariable("id") String id) {
        return new PaymentStatusResponse(id, paymentService.getPaymentStatus(id));
    }

    @PutMapping("/{id}/status")
    public PaymentResponse updateStatus(@PathVariable("id") String id, @Valid @RequestBody UpdatePaymentStatusRequest request) {
        Payment updated = paymentService.updatePaymentStatus(id, request.status());
        return toResponse(updated);
    }

    private static PaymentResponse toResponse(Payment p) {
        return new PaymentResponse(
                p.getId(),
                p.getConcept(),
                p.getProductCount(),
                p.getPayer(),
                p.getPayee(),
                p.getTotalAmount(),
                p.getStatus(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
