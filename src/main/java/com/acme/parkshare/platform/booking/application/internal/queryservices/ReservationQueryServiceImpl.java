package com.acme.parkshare.platform.booking.application.internal.queryservices;

import com.acme.parkshare.platform.booking.application.queries.GetAllReservationsQuery;
import com.acme.parkshare.platform.booking.application.queries.GetReservationByIdQuery;
import com.acme.parkshare.platform.booking.application.queryservices.ReservationQueryService;
import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;
import com.acme.parkshare.platform.booking.domain.repositories.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReservationQueryServiceImpl implements ReservationQueryService {

    private final ReservationRepository repository;

    public ReservationQueryServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Reservation> handle(GetAllReservationsQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<Reservation> handle(GetReservationByIdQuery query) {
        return repository.findById(query.id());
    }
}
