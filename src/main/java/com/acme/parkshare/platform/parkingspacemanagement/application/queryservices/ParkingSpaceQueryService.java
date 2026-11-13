package com.acme.parkshare.platform.parkingspacemanagement.application.queryservices;

import com.acme.parkshare.platform.parkingspacemanagement.application.queries.GetAllParkingSpacesByOwnerIdQuery;
import com.acme.parkshare.platform.parkingspacemanagement.application.queries.GetAllParkingSpacesQuery;
import com.acme.parkshare.platform.parkingspacemanagement.application.queries.GetParkingSpaceByIdQuery;
import com.acme.parkshare.platform.parkingspacemanagement.domain.model.aggregates.ParkingSpace;

import java.util.List;
import java.util.Optional;

public interface ParkingSpaceQueryService {
    List<ParkingSpace> handle(GetAllParkingSpacesQuery query);
    Optional<ParkingSpace> handle(GetParkingSpaceByIdQuery query);
    List<ParkingSpace> handle(GetAllParkingSpacesByOwnerIdQuery query);
}
