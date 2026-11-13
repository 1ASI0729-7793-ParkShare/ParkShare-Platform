package com.acme.parkshare.platform.booking.application.commands;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateReservationCommand(
        @NotNull Long parkingListingId,
        @NotNull Long ownerId,
        @NotNull Long driverId,
        @NotBlank String driverName,
        @NotBlank String vehiclePlate,
        @NotBlank String vehicleModel,
        @NotBlank String spaceName,
        @NotBlank String spaceAddress,
        @NotNull BigDecimal hourlyRate,
        @NotNull Instant plannedEntry,
        @Min(1) int plannedHours
) {}
