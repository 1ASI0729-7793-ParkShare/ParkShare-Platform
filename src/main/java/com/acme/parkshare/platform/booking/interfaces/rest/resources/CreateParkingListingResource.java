package com.acme.parkshare.platform.booking.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateParkingListingResource(
        @NotNull Long ownerId,
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String district,
        @NotNull @Positive BigDecimal hourlyRate,
        @NotNull Boolean covered,
        @NotNull Double latitude,
        @NotNull Double longitude
) {}
