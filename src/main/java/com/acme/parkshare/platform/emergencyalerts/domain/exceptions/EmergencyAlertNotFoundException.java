package com.acme.parkshare.platform.emergencyalerts.domain.exceptions;

public class EmergencyAlertNotFoundException extends RuntimeException {
    public EmergencyAlertNotFoundException(Long id) {
        super("Emergency alert not found: " + id);
    }
}
