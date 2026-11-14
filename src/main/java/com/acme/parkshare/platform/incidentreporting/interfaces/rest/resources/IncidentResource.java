package com.acme.parkshare.platform.incidentreporting.interfaces.rest.resources;
import java.time.Instant;
import java.util.List;
public record IncidentResource(Long id, Long reporterId, String reporterName,
        Long parkingListingId, Long ownerId, String spaceName, String spaceAddress,
        List<String> types, String description, List<String> photos, String status,
        Instant createdAt, Instant resolvedAt, Instant updatedAt) {}
