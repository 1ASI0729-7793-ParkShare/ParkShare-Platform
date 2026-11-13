package com.acme.parkshare.platform.booking.application.commandservices;

public sealed interface ParkingListingCommandFailure
        permits ParkingListingCommandFailure.NotFound, ParkingListingCommandFailure.InvalidStatus {

    record NotFound() implements ParkingListingCommandFailure {}

    record InvalidStatus(String reason) implements ParkingListingCommandFailure {}
}
