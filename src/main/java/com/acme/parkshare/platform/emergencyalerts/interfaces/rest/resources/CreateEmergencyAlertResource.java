package com.acme.parkshare.platform.emergencyalerts.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEmergencyAlertResource(
        @NotNull Long reporterId,
        @NotNull Long parkingListingId,
        @NotNull Long ownerId,
        @NotNull Double latitude,
        @NotNull Double longitude,
        @NotBlank String description
) {}
