package com.acme.parkshare.platform.booking.application.commands;

import java.math.BigDecimal;

public record UpdateParkingListingCommand(
        Long id,
        String name,
        String address,
        String district,
        BigDecimal hourlyRate,
        boolean covered,
        String availability,
        Double latitude,
        Double longitude
) {}
