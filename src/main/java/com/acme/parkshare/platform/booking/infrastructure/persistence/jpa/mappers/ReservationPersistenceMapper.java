package com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;
import com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.entities.ReservationJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationPersistenceMapper {

    public ReservationJpaEntity toEntity(Reservation r) {
        return new ReservationJpaEntity(
                r.getId(), r.getParkingListingId(), r.getOwnerId(), r.getDriverId(),
                r.getDriverName(), r.getVehiclePlate(), r.getVehicleModel(),
                r.getSpaceName(), r.getSpaceAddress(), r.getHourlyRate(),
                r.getPlannedEntry(), r.getPlannedHours(), r.getStatus(),
                r.getStartedAt(), r.getEndedAt(), r.getTotalCost(),
                r.getCreatedAt(), r.getUpdatedAt()
        );
    }

    public Reservation toDomain(ReservationJpaEntity e) {
        return Reservation.rehydrate(
                e.getId(), e.getParkingListingId(), e.getOwnerId(), e.getDriverId(),
                e.getDriverName(), e.getVehiclePlate(), e.getVehicleModel(),
                e.getSpaceName(), e.getSpaceAddress(), e.getHourlyRate(),
                e.getPlannedEntry(), e.getPlannedHours(), e.getStatus(),
                e.getStartedAt(), e.getEndedAt(), e.getTotalCost(),
                e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
