package com.acme.parkshare.platform.incidentreporting.domain.exceptions;
public class IncidentNotFoundException extends RuntimeException {
    public IncidentNotFoundException(Long id) { super("Incident not found: " + id); }
}
