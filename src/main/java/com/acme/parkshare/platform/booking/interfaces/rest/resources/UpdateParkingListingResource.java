package com.acme.parkshare.platform.booking.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public record UpdateParkingListingResource(
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String district,
        @NotNull @Positive BigDecimal hourlyRate,
        @NotNull Boolean covered,
        @NotBlank String availability,
        @NotNull Double latitude,
        @NotNull Double longitude,
        List<String> features,
        List<String> photos,
        String publicationStatus,
        List<ListingAvailabilityPeriodResource> availabilityPeriods
) {}
