package com.acme.parkshare.platform.booking.application.queryservices;

import com.acme.parkshare.platform.booking.application.queries.GetAllParkingListingsQuery;
import com.acme.parkshare.platform.booking.application.queries.GetParkingListingByIdQuery;
import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;

import java.util.List;
import java.util.Optional;

public interface ParkingListingQueryService {
    List<ParkingListing> handle(GetAllParkingListingsQuery query);
    Optional<ParkingListing> handle(GetParkingListingByIdQuery query);
}
