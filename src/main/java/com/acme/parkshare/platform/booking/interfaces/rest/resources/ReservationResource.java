package com.acme.parkshare.platform.booking.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.Instant;

public record ReservationResource(
        Long id,
        Long parkingListingId,
        Long ownerId,
        Long driverId,
        String driverName,
        String vehiclePlate,
        String vehicleModel,
        String spaceName,
        String spaceAddress,
        BigDecimal hourlyRate,
        Instant plannedEntry,
        int plannedHours,
        String status,
        Instant startedAt,
        Instant endedAt,
        BigDecimal totalCost,
        Instant createdAt,
        Instant updatedAt
) {}
