package com.acme.parkshare.platform.emergencyalerts.interfaces.rest.resources;

import java.time.Instant;

public record EmergencyAlertResource(
        Long id,
        Long ownerId,
        Long parkingListingId,
        String spaceName,
        String kind,
        String notes,
        Double latitude,
        Double longitude,
        String locationSource,
        String status,
        Instant createdAt,
        Instant resolvedAt
) {}
