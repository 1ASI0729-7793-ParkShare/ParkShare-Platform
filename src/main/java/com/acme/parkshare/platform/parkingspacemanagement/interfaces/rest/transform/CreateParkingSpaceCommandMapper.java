package com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.transform;

import com.acme.parkshare.platform.parkingspacemanagement.application.commands.CreateParkingSpaceCommand;
import com.acme.parkshare.platform.parkingspacemanagement.domain.model.valueobjects.AvailabilityPeriod;
import com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.resources.CreateParkingSpaceResource;

import java.util.Collections;
import java.util.List;

public class CreateParkingSpaceCommandMapper {
    private CreateParkingSpaceCommandMapper() {}

    public static CreateParkingSpaceCommand toCommand(CreateParkingSpaceResource resource) {
        List<AvailabilityPeriod> periods = resource.availabilityPeriods() == null ? Collections.emptyList() :
                resource.availabilityPeriods().stream()
                        .map(p -> new AvailabilityPeriod(p.dayOfWeek(), p.startTime(), p.endTime()))
                        .toList();

        return new CreateParkingSpaceCommand(
                resource.ownerId(),
                resource.address(),
                resource.name(),
                resource.hourlyRate(),
                resource.features() != null ? resource.features() : "",
                periods
        );
    }
}
