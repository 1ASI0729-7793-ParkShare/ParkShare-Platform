package com.acme.parkshare.platform.booking.interfaces.rest.transform;

import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.ParkingListingResource;

public class ParkingListingResourceMapper {
    private ParkingListingResourceMapper() {}

    public static ParkingListingResource toResource(ParkingListing listing) {
        return new ParkingListingResource(
                listing.getId(), listing.getOwnerId(), listing.getName(),
                listing.getAddress(), listing.getDistrict(), listing.getHourlyRate(),
                listing.isCovered(), listing.getRating(), listing.getReviewCount(),
                listing.getAvailability(), listing.getLatitude(), listing.getLongitude(),
                listing.getCreatedAt(), listing.getUpdatedAt()
        );
    }
}
