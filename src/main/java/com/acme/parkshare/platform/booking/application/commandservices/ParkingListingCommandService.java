package com.acme.parkshare.platform.booking.application.commandservices;

import com.acme.parkshare.platform.booking.application.commands.CreateParkingListingCommand;
import com.acme.parkshare.platform.booking.application.commands.UpdateParkingListingCommand;
import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;
import com.acme.parkshare.platform.shared.application.result.Result;

public interface ParkingListingCommandService {
    Result<ParkingListing, ParkingListingCommandFailure> handle(CreateParkingListingCommand command);
    Result<ParkingListing, ParkingListingCommandFailure> handle(UpdateParkingListingCommand command);
}
