package com.acme.parkshare.platform.booking.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.entities.ReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {
    List<ReservationJpaEntity> findAllByOwnerId(Long ownerId);
    List<ReservationJpaEntity> findAllByDriverId(Long driverId);
}
