package com.acme.parkshare.platform.booking.domain.repositories;

import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    List<Reservation> findAll();
    List<Reservation> findAllByOwnerId(Long ownerId);
    List<Reservation> findAllByDriverId(Long driverId);
}
