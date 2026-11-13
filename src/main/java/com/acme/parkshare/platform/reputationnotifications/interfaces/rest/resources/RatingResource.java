package com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources;

import java.time.Instant;

public record RatingResource(
        Long id,
        Long bookingId,
        Long reviewerId,
        Long revieweeId,
        int score,
        String comment,
        Instant createdAt
) {}
