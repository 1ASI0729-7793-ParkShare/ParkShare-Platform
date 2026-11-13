package com.acme.parkshare.platform.emergencyalerts.application.commands;

public record CreateEmergencyAlertCommand(
        Long reporterId,
        Long parkingListingId,
        Long ownerId,
        Double latitude,
        Double longitude,
        String description
) {}
