package com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record AvailabilityPeriodResource(
        @NotBlank String dayOfWeek,
        @NotBlank String startTime,
        @NotBlank String endTime
) {}
