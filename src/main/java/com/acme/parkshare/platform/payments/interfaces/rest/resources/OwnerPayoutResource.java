package com.acme.parkshare.platform.payments.interfaces.rest.resources;

import java.math.BigDecimal;

public record OwnerPayoutResource(
        Long id,
        Long ownerId,
        BigDecimal availableBalance,
        BigDecimal completedAmount,
        BigDecimal inTransitAmount,
        String linkedAccount,
        BigDecimal commissionRate) {}
