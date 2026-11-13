package com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public record CreateParkingSpaceResource(
        @NotNull Long ownerId,
        @NotBlank String address,
        @NotBlank String name,
        @NotNull @Positive BigDecimal hourlyRate,
        String features,
        List<AvailabilityPeriodResource> availabilityPeriods
) {}
