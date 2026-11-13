package com.acme.parkshare.platform.incidentreporting.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.incidentreporting.domain.model.aggregates.Incident;
import com.acme.parkshare.platform.incidentreporting.infrastructure.persistence.jpa.entities.IncidentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class IncidentPersistenceMapper {
    public IncidentJpaEntity toEntity(Incident i) {
        return new IncidentJpaEntity(i.getId(), i.getReporterId(), i.getReporterName(),
                i.getParkingListingId(), i.getOwnerId(), i.getSpaceName(), i.getSpaceAddress(),
                i.getIncidentTypes(), i.getDescription(), i.getPhotos(), i.getStatus(),
                i.getCreatedAt(), i.getResolvedAt(), i.getUpdatedAt());
    }
    public Incident toDomain(IncidentJpaEntity e) {
        return Incident.rehydrate(e.getId(), e.getReporterId(), e.getReporterName(),
                e.getParkingListingId(), e.getOwnerId(), e.getSpaceName(), e.getSpaceAddress(),
                e.getIncidentTypes(), e.getDescription(), e.getPhotos(), e.getStatus(),
                e.getCreatedAt(), e.getResolvedAt(), e.getUpdatedAt());
    }
}
