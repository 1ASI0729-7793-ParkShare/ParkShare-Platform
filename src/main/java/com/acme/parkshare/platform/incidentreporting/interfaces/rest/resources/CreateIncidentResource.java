package com.acme.parkshare.platform.incidentreporting.interfaces.rest.resources;
import jakarta.validation.constraints.*;

import java.util.List;
public record CreateIncidentResource(
        @NotNull Long reporterId, @NotBlank String reporterName,
        @NotNull Long parkingListingId, @NotNull Long ownerId,
        @NotBlank String spaceName, @NotBlank String spaceAddress,
        @NotEmpty List<String> types,
        @NotBlank @Size(min=10, max=500) String description,
        List<String> photos) {}
