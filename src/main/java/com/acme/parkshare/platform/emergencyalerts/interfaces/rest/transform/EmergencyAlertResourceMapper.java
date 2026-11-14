package com.acme.parkshare.platform.emergencyalerts.interfaces.rest.transform;

import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;
import com.acme.parkshare.platform.emergencyalerts.interfaces.rest.resources.EmergencyAlertResource;

public class EmergencyAlertResourceMapper {

    private EmergencyAlertResourceMapper() {}

    public static EmergencyAlertResource toResource(EmergencyAlert a) {
        return new EmergencyAlertResource(a.getId(), a.getOwnerId(), a.getParkingListingId(), a.getSpaceName(),
                a.getKind(), a.getNotes(), a.getLatitude(), a.getLongitude(), a.getLocationSource(),
                a.getStatus(), a.getCreatedAt(), a.getResolvedAt());
    }
}
