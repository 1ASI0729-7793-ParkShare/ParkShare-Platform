package com.acme.parkshare.platform.incidentreporting.interfaces.rest.resources;
import java.time.Instant;
public record IncidentResource(Long id, Long reporterId, String reporterName,
        Long parkingListingId, Long ownerId, String spaceName, String spaceAddress,
        String incidentTypes, String description, String photos, String status,
        Instant createdAt, Instant resolvedAt, Instant updatedAt) {}
