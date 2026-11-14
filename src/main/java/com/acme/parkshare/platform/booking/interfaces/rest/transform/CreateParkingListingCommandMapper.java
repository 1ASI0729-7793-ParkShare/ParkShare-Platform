package com.acme.parkshare.platform.booking.interfaces.rest.transform;

import com.acme.parkshare.platform.booking.application.commands.CreateParkingListingCommand;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.CreateParkingListingResource;

import com.acme.parkshare.platform.shared.interfaces.rest.transform.JsonText;

public class CreateParkingListingCommandMapper {
    private CreateParkingListingCommandMapper() {}

    public static CreateParkingListingCommand toCommand(CreateParkingListingResource resource) {
        return new CreateParkingListingCommand(
                resource.ownerId(), resource.name(), resource.address(), resource.district(),
                resource.hourlyRate(), resource.covered(), resource.latitude(), resource.longitude(),
                JsonText.toJson(resource.features()), JsonText.toJson(resource.photos()),
                resource.publicationStatus(), JsonText.toJson(resource.availabilityPeriods())
        );
    }
}
