package com.acme.parkshare.platform.emergencyalerts.interfaces.rest.resources;

import java.time.Instant;

public record EmergencyAlertResource(
        Long id,
        Long reporterId,
        Long parkingListingId,
        Long ownerId,
        Double latitude,
        Double longitude,
        String description,
        String status,
        Instant createdAt,
        Instant resolvedAt
) {}
