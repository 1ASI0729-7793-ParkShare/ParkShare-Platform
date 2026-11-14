package com.acme.parkshare.platform.payments.domain.model.aggregates;

import java.math.BigDecimal;
import java.time.Instant;

/** One earning of an owner, generated when a reservation is completed. */
public record PaymentTransaction(
        Long id,
        Long ownerId,
        Long reservationId,
        String driverName,
        String garageName,
        Instant date,
        int durationMinutes,
        BigDecimal amountReceived) {}
