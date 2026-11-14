package com.acme.parkshare.platform.emergencyalerts.application.commands;

public record CreateEmergencyAlertCommand(
        Long ownerId,
        Long parkingListingId,
        String spaceName,
        String kind,
        String notes,
        Double latitude,
        Double longitude,
        String locationSource
) {}
