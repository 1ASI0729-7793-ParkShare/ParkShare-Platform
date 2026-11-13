package com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.transform;

import com.acme.parkshare.platform.parkingspacemanagement.domain.model.aggregates.ParkingSpace;
import com.acme.parkshare.platform.parkingspacemanagement.domain.model.valueobjects.AvailabilityPeriod;
import com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.resources.AvailabilityPeriodResource;
import com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.resources.ParkingSpaceResource;

import java.util.Collections;
import java.util.List;

public class ParkingSpaceResourceMapper {
    private ParkingSpaceResourceMapper() {}

    public static ParkingSpaceResource toResource(ParkingSpace space) {
        List<AvailabilityPeriodResource> periods = space.getAvailabilityPeriods() == null ? Collections.emptyList() :
                space.getAvailabilityPeriods().stream()
                        .map(p -> new AvailabilityPeriodResource(p.dayOfWeek(), p.startTime(), p.endTime()))
                        .toList();

        return new ParkingSpaceResource(
                space.getId(),
                space.getOwnerId(),
                space.getAddress(),
                space.getName(),
                space.getHourlyRate(),
                space.getFeatures(),
                space.getPublicationStatus(),
                space.getAccumulatedIncome(),
                periods,
                space.getCreatedAt(),
                space.getUpdatedAt()
        );
    }
}
