package com.acme.parkshare.platform.emergencyalerts.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateEmergencyAlertResource(
        @NotNull Long ownerId,
        Long parkingListingId,
        String spaceName,
        @NotBlank @Pattern(regexp = "fire|theft|accident|other", message = "kind must be fire, theft, accident or other")
        String kind,
        @Size(max = 300) String notes,
        Double latitude,
        Double longitude,
        @Pattern(regexp = "gps|space", message = "locationSource must be gps or space") String locationSource
) {}
