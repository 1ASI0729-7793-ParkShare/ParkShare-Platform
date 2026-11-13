package com.acme.parkshare.platform.booking.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.entities.ParkingListingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingListingJpaRepository extends JpaRepository<ParkingListingJpaEntity, Long> {
    List<ParkingListingJpaEntity> findAllByOwnerId(Long ownerId);
}
