package com.acme.parkshare.platform.incidentreporting.application.commands;
public record UpdateIncidentStatusCommand(Long id, String newStatus) {}
