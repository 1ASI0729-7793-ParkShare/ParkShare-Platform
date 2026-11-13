package com.acme.parkshare.platform.booking.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateReservationStatusResource(
        @NotBlank String status
) {}
