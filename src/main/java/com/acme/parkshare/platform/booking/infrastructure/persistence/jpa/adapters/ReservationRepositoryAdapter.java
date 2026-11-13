package com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;
import com.acme.parkshare.platform.booking.domain.repositories.ReservationRepository;
import com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.ReservationJpaRepository;
import com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.mappers.ReservationPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final ReservationJpaRepository jpaRepository;
    private final ReservationPersistenceMapper mapper;

    public ReservationRepositoryAdapter(ReservationJpaRepository jpaRepository,
                                        ReservationPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(reservation)));
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Reservation> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Reservation> findAllByOwnerId(Long ownerId) {
        return jpaRepository.findAllByOwnerId(ownerId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Reservation> findAllByDriverId(Long driverId) {
        return jpaRepository.findAllByDriverId(driverId).stream().map(mapper::toDomain).toList();
    }
}
