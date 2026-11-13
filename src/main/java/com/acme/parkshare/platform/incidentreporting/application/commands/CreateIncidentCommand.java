package com.acme.parkshare.platform.incidentreporting.application.commands;
public record CreateIncidentCommand(Long reporterId, String reporterName, Long parkingListingId,
        Long ownerId, String spaceName, String spaceAddress, String incidentTypes,
        String description, String photos) {}
