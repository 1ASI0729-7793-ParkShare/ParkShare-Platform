package com.acme.parkshare.platform.parkingspacemanagement.domain.repositories;

import com.acme.parkshare.platform.parkingspacemanagement.domain.model.aggregates.ParkingSpace;
import java.util.List;
import java.util.Optional;

public interface ParkingSpaceRepository {
    ParkingSpace save(ParkingSpace space);
    Optional<ParkingSpace> findById(Long id);
    List<ParkingSpace> findAll();
    List<ParkingSpace> findAllByOwnerId(Long ownerId);
    void deleteById(Long id);
}
