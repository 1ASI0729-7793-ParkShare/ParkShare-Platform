package com.acme.parkshare.platform.profile.application.commandservices;

public sealed interface ProfileCommandFailure permits ProfileCommandFailure.NotFound, ProfileCommandFailure.InvalidVehicleType {
    record NotFound() implements ProfileCommandFailure {}
    record InvalidVehicleType(String vehicleType) implements ProfileCommandFailure {}
}
