package com.acme.parkshare.platform.booking.application.queryservices;

import com.acme.parkshare.platform.booking.application.queries.GetAllReservationsQuery;
import com.acme.parkshare.platform.booking.application.queries.GetReservationByIdQuery;
import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationQueryService {
    List<Reservation> handle(GetAllReservationsQuery query);
    Optional<Reservation> handle(GetReservationByIdQuery query);
}
