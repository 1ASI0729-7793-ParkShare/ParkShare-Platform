package com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateRatingResource(
        @NotNull Long bookingId,
        @NotNull Long reviewerId,
        @NotNull Long revieweeId,
        @NotNull @Min(1) @Max(5) Integer score,
        String comment
) {}
