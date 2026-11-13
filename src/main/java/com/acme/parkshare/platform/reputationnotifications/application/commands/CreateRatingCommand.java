package com.acme.parkshare.platform.reputationnotifications.application.commands;

public record CreateRatingCommand(
        Long bookingId,
        Long reviewerId,
        Long revieweeId,
        int score,
        String comment
) {}
