package com.acme.parkshare.platform.incidentreporting.interfaces.rest.resources;
import jakarta.validation.constraints.*;
public record CreateIncidentResource(
        @NotNull Long reporterId, @NotBlank String reporterName,
        @NotNull Long parkingListingId, @NotNull Long ownerId,
        @NotBlank String spaceName, @NotBlank String spaceAddress,
        @NotBlank String incidentTypes,
        @NotBlank @Size(min=10, max=500) String description,
        String photos) {}
