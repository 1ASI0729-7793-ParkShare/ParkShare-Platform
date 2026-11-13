package com.acme.parkshare.platform.booking.application.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateReservationStatusCommand(
        @NotNull Long id,
        @NotBlank String newStatus
) {}
