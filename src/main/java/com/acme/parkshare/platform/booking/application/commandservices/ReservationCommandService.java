package com.acme.parkshare.platform.booking.application.commandservices;

import com.acme.parkshare.platform.booking.application.commands.CreateReservationCommand;
import com.acme.parkshare.platform.booking.application.commands.UpdateReservationStatusCommand;
import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;
import com.acme.parkshare.platform.shared.application.result.Result;

public interface ReservationCommandService {
    Result<Reservation, ReservationCommandFailure> handle(CreateReservationCommand command);
    Result<Reservation, ReservationCommandFailure> handle(UpdateReservationStatusCommand command);
}
