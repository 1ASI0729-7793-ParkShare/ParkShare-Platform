package com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ParkingSpaceResource(
        Long id,
        Long ownerId,
        String address,
        String name,
        BigDecimal hourlyRate,
        String features,
        String publicationStatus,
        BigDecimal accumulatedIncome,
        List<AvailabilityPeriodResource> availabilityPeriods,
        Instant createdAt,
        Instant updatedAt
) {}
