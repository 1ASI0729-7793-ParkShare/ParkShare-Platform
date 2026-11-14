package com.acme.parkshare.platform.booking.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ParkingListingResource(
        Long id,
        Long ownerId,
        String name,
        String address,
        String district,
        BigDecimal hourlyRate,
        boolean covered,
        BigDecimal rating,
        int reviewCount,
        String availability,
        Double latitude,
        Double longitude,
        Instant createdAt,
        Instant updatedAt,
        List<String> features,
        List<String> photos,
        String publicationStatus,
        List<ListingAvailabilityPeriodResource> availabilityPeriods
) {}
