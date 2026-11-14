package com.acme.parkshare.platform.booking.application.commands;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateParkingListingCommand(
        @NotNull Long ownerId,
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String district,
        @NotNull @DecimalMin("0.0") BigDecimal hourlyRate,
        boolean covered,
        @NotNull Double latitude,
        @NotNull Double longitude,
        String features,
        String photos,
        String publicationStatus,
        String availabilityPeriods
) {}
