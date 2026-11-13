package com.acme.parkshare.platform.booking.domain.repositories;

import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;

import java.util.List;
import java.util.Optional;

public interface ParkingListingRepository {
    ParkingListing save(ParkingListing listing);
    Optional<ParkingListing> findById(Long id);
    List<ParkingListing> findAll();
    List<ParkingListing> findAllByOwnerId(Long ownerId);
    void deleteById(Long id);
}
