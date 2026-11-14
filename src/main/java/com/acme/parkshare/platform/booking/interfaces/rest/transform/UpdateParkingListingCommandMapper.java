package com.acme.parkshare.platform.booking.interfaces.rest.transform;

import com.acme.parkshare.platform.booking.application.commands.UpdateParkingListingCommand;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.UpdateParkingListingResource;

import com.acme.parkshare.platform.shared.interfaces.rest.transform.JsonText;

public class UpdateParkingListingCommandMapper {
    private UpdateParkingListingCommandMapper() {}

    public static UpdateParkingListingCommand toCommand(Long id, UpdateParkingListingResource resource) {
        return new UpdateParkingListingCommand(
                id, resource.name(), resource.address(), resource.district(),
                resource.hourlyRate(), resource.covered(), resource.availability(),
                resource.latitude(), resource.longitude(),
                JsonText.toJson(resource.features()), JsonText.toJson(resource.photos()),
                resource.publicationStatus(), JsonText.toJson(resource.availabilityPeriods())
        );
    }
}
