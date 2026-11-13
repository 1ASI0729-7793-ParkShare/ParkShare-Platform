package com.acme.parkshare.platform.booking.interfaces.rest.transform;

import com.acme.parkshare.platform.booking.application.commands.CreateReservationCommand;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.CreateReservationResource;

public class CreateReservationCommandMapper {
    private CreateReservationCommandMapper() {}

    public static CreateReservationCommand toCommand(CreateReservationResource resource) {
        return new CreateReservationCommand(
                resource.parkingListingId(), resource.ownerId(), resource.driverId(),
                resource.driverName(), resource.vehiclePlate(), resource.vehicleModel(),
                resource.spaceName(), resource.spaceAddress(), resource.hourlyRate(),
                resource.plannedEntry(), resource.plannedHours()
        );
    }
}
