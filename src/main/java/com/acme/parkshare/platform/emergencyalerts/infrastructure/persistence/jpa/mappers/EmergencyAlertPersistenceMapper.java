package com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;
import com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa.entities.EmergencyAlertJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class EmergencyAlertPersistenceMapper {

    public EmergencyAlertJpaEntity toEntity(EmergencyAlert alert) {
        return new EmergencyAlertJpaEntity(
                alert.getId(),
                alert.getReporterId(),
                alert.getParkingListingId(),
                alert.getOwnerId(),
                alert.getLatitude(),
                alert.getLongitude(),
                alert.getDescription(),
                alert.getStatus(),
                alert.getCreatedAt(),
                alert.getResolvedAt(),
                alert.getUpdatedAt()
        );
    }

    public EmergencyAlert toDomain(EmergencyAlertJpaEntity entity) {
        return EmergencyAlert.rehydrate(
                entity.getId(),
                entity.getReporterId(),
                entity.getParkingListingId(),
                entity.getOwnerId(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getResolvedAt(),
                entity.getUpdatedAt()
        );
    }
}
