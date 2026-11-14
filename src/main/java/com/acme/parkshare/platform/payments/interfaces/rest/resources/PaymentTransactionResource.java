package com.acme.parkshare.platform.payments.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentTransactionResource(
        Long id,
        Long ownerId,
        Long reservationId,
        String driverName,
        String garageName,
        Instant date,
        int durationMinutes,
        BigDecimal amountReceived) {}
