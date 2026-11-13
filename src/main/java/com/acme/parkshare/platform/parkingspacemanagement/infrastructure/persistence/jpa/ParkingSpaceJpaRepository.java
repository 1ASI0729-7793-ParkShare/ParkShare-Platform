package com.acme.parkshare.platform.parkingspacemanagement.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.parkingspacemanagement.infrastructure.persistence.jpa.entities.ParkingSpaceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpaceJpaRepository extends JpaRepository<ParkingSpaceJpaEntity, Long> {
    List<ParkingSpaceJpaEntity> findAllByOwnerId(Long ownerId);
}
