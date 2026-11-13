package com.acme.parkshare.platform.parkingspacemanagement.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.parkingspacemanagement.domain.model.aggregates.ParkingSpace;
import com.acme.parkshare.platform.parkingspacemanagement.domain.model.valueobjects.AvailabilityPeriod;
import com.acme.parkshare.platform.parkingspacemanagement.infrastructure.persistence.jpa.entities.AvailabilityPeriodEmbeddable;
import com.acme.parkshare.platform.parkingspacemanagement.infrastructure.persistence.jpa.entities.ParkingSpaceJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ParkingSpacePersistenceMapper {

    public ParkingSpaceJpaEntity toEntity(ParkingSpace space) {
        List<AvailabilityPeriodEmbeddable> periods = space.getAvailabilityPeriods() == null ? Collections.emptyList() :
                space.getAvailabilityPeriods().stream()
                        .map(p -> new AvailabilityPeriodEmbeddable(p.dayOfWeek(), p.startTime(), p.endTime()))
                        .toList();

        return new ParkingSpaceJpaEntity(
                space.getId(),
                space.getOwnerId(),
                space.getAddress(),
                space.getName(),
                space.getHourlyRate(),
                space.getFeatures(),
                space.getPublicationStatus(),
                space.getAccumulatedIncome(),
                periods,
                space.getCreatedAt(),
                space.getUpdatedAt()
        );
    }

    public ParkingSpace toDomain(ParkingSpaceJpaEntity entity) {
        List<AvailabilityPeriod> periods = entity.getAvailabilityPeriods() == null ? Collections.emptyList() :
                entity.getAvailabilityPeriods().stream()
                        .map(e -> new AvailabilityPeriod(e.getDayOfWeek(), e.getStartTime(), e.getEndTime()))
                        .toList();

        return ParkingSpace.rehydrate(
                entity.getId(),
                entity.getOwnerId(),
                entity.getAddress(),
                entity.getName(),
                entity.getHourlyRate(),
                entity.getFeatures(),
                entity.getPublicationStatus(),
                entity.getAccumulatedIncome(),
                periods,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
