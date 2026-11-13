package com.acme.parkshare.platform.parkingspacemanagement.domain.exceptions;
public class ParkingSpaceNotFoundException extends RuntimeException {
    public ParkingSpaceNotFoundException(Long id) { super("Parking space not found: " + id); }
}
