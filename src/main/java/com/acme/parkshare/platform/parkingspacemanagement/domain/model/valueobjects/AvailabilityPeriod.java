package com.acme.parkshare.platform.parkingspacemanagement.domain.model.valueobjects;

public record AvailabilityPeriod(String dayOfWeek, String startTime, String endTime) {
    public AvailabilityPeriod {
        if (dayOfWeek == null || dayOfWeek.isBlank()) throw new IllegalArgumentException("dayOfWeek required");
        if (startTime == null || startTime.isBlank()) throw new IllegalArgumentException("startTime required");
        if (endTime == null || endTime.isBlank()) throw new IllegalArgumentException("endTime required");
    }
}
