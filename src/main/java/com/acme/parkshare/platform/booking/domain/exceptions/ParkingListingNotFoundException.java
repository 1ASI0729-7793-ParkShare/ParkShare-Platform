package com.acme.parkshare.platform.booking.domain.exceptions;

public class ParkingListingNotFoundException extends RuntimeException {
    public ParkingListingNotFoundException(Long id) {
        super("Parking listing not found with id: " + id);
    }
}
