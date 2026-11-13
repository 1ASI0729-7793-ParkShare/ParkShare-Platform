package com.acme.parkshare.platform.emergencyalerts.interfaces.rest.transform;

import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;
import com.acme.parkshare.platform.emergencyalerts.interfaces.rest.resources.EmergencyAlertResource;

public class EmergencyAlertResourceMapper {
    private EmergencyAlertResourceMapper() {}

    public static EmergencyAlertResource toResource(EmergencyAlert a) {
        return new EmergencyAlertResource(
                a.getId(),
                a.getReporterId(),
                a.getParkingListingId(),
                a.getOwnerId(),
                a.getLatitude(),
                a.getLongitude(),
                a.getDescription(),
                a.getStatus(),
                a.getCreatedAt(),
                a.getResolvedAt()
        );
    }
}
