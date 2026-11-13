package com.acme.parkshare.platform.parkingspacemanagement.application.commandservices;

public sealed interface ParkingSpaceCommandFailure permits
        ParkingSpaceCommandFailure.NotFound,
        ParkingSpaceCommandFailure.InvalidState {
    record NotFound() implements ParkingSpaceCommandFailure {}
    record InvalidState(String reason) implements ParkingSpaceCommandFailure {}
}
