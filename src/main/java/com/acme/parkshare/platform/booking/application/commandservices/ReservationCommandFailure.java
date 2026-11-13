package com.acme.parkshare.platform.booking.application.commandservices;

public sealed interface ReservationCommandFailure
        permits ReservationCommandFailure.NotFound, ReservationCommandFailure.InvalidStatus {

    record NotFound() implements ReservationCommandFailure {}

    record InvalidStatus(String reason) implements ReservationCommandFailure {}
}
