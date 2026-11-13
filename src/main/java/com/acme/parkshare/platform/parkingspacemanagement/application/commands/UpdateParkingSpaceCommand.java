package com.acme.parkshare.platform.parkingspacemanagement.application.commands;

import com.acme.parkshare.platform.parkingspacemanagement.domain.model.valueobjects.AvailabilityPeriod;
import java.math.BigDecimal;
import java.util.List;

public record UpdateParkingSpaceCommand(
        Long id,
        String address,
        String name,
        BigDecimal hourlyRate,
        String features,
        String publicationStatus,
        List<AvailabilityPeriod> availabilityPeriods
) {}
