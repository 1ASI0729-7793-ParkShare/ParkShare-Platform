package com.acme.parkshare.platform.profile.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateProfileResource(
        @NotBlank String fullName,
        @NotBlank @Pattern(regexp = "^[A-Za-z0-9]{3}-[A-Za-z0-9]{3}$", message = "Invalid plate format") String plate,
        @NotBlank String vehicleModel,
        @NotBlank String vehicleType
) {}
