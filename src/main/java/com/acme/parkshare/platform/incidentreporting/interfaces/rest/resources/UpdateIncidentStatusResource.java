package com.acme.parkshare.platform.incidentreporting.interfaces.rest.resources;
import jakarta.validation.constraints.NotBlank;
public record UpdateIncidentStatusResource(@NotBlank String status) {}
