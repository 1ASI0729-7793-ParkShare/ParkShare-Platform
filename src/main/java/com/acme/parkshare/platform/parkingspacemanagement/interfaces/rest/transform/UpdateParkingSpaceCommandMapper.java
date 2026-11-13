package com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.transform;

import com.acme.parkshare.platform.parkingspacemanagement.application.commands.UpdateParkingSpaceCommand;
import com.acme.parkshare.platform.parkingspacemanagement.domain.model.valueobjects.AvailabilityPeriod;
import com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.resources.UpdateParkingSpaceResource;

import java.util.Collections;
import java.util.List;

public class UpdateParkingSpaceCommandMapper {
    private UpdateParkingSpaceCommandMapper() {}

    public static UpdateParkingSpaceCommand toCommand(Long id, UpdateParkingSpaceResource resource) {
        List<AvailabilityPeriod> periods = resource.availabilityPeriods() == null ? Collections.emptyList() :
                resource.availabilityPeriods().stream()
                        .map(p -> new AvailabilityPeriod(p.dayOfWeek(), p.startTime(), p.endTime()))
                        .toList();

        return new UpdateParkingSpaceCommand(
                id,
                resource.address(),
                resource.name(),
                resource.hourlyRate(),
                resource.features() != null ? resource.features() : "",
                resource.publicationStatus(),
                periods
        );
    }
}
