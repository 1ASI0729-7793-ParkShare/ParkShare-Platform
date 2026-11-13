package com.acme.parkshare.platform.booking.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;

public record CreateReservationResource(
        @NotNull Long parkingListingId,
        @NotNull Long ownerId,
        @NotNull Long driverId,
        @NotBlank String driverName,
        @NotBlank String vehiclePlate,
        @NotBlank String vehicleModel,
        @NotBlank String spaceName,
        @NotBlank String spaceAddress,
        @NotNull @Positive BigDecimal hourlyRate,
        @NotNull Instant plannedEntry,
        @NotNull @Positive Integer plannedHours
) {}
