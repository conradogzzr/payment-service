package com.conrado.payment_service.domain.model;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public final class PaymentStatusTransitionPolicy {

    private static final Map<PaymentStatus, Set<PaymentStatus>> ALLOWED =
            Map.of(
                    PaymentStatus.PENDING, EnumSet.of(PaymentStatus.PROCESSING, PaymentStatus.CANCELLED),
                    PaymentStatus.PROCESSING, EnumSet.of(PaymentStatus.COMPLETED, PaymentStatus.FAILED, PaymentStatus.CANCELLED),
                    PaymentStatus.COMPLETED, EnumSet.noneOf(PaymentStatus.class),
                    PaymentStatus.FAILED, EnumSet.noneOf(PaymentStatus.class),
                    PaymentStatus.CANCELLED, EnumSet.noneOf(PaymentStatus.class)
            );

    private PaymentStatusTransitionPolicy() { }

    public static boolean canTransition(PaymentStatus from, PaymentStatus to) {
        return ALLOWED.getOrDefault(from, EnumSet.noneOf(PaymentStatus.class)).contains(to);
    }

}
