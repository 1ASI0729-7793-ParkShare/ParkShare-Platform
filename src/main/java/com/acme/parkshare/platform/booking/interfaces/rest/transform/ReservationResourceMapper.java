package com.acme.parkshare.platform.booking.interfaces.rest.transform;

import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.ReservationResource;

public class ReservationResourceMapper {
    private ReservationResourceMapper() {}

    public static ReservationResource toResource(Reservation r) {
        return new ReservationResource(
                r.getId(), r.getParkingListingId(), r.getOwnerId(), r.getDriverId(),
                r.getDriverName(), r.getVehiclePlate(), r.getVehicleModel(),
                r.getSpaceName(), r.getSpaceAddress(), r.getHourlyRate(),
                r.getPlannedEntry(), r.getPlannedHours(), r.getStatus(),
                r.getStartedAt(), r.getEndedAt(), r.getTotalCost(),
                r.getCreatedAt(), r.getUpdatedAt()
        );
    }
}
