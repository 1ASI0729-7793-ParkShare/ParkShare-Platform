package com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;
import com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa.entities.EmergencyAlertJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class EmergencyAlertPersistenceMapper {

    public EmergencyAlertJpaEntity toEntity(EmergencyAlert a) {
        return new EmergencyAlertJpaEntity(a.getId(), a.getOwnerId(), a.getParkingListingId(), a.getSpaceName(),
                a.getKind(), a.getNotes(), a.getLatitude(), a.getLongitude(), a.getLocationSource(),
                a.getStatus(), a.getCreatedAt(), a.getResolvedAt(), a.getUpdatedAt());
    }

    public EmergencyAlert toDomain(EmergencyAlertJpaEntity e) {
        return EmergencyAlert.rehydrate(e.getId(), e.getOwnerId(), e.getParkingListingId(), e.getSpaceName(),
                e.getKind(), e.getNotes(), e.getLatitude(), e.getLongitude(), e.getLocationSource(),
                e.getStatus(), e.getCreatedAt(), e.getResolvedAt(), e.getUpdatedAt());
    }
}
