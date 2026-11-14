package com.acme.parkshare.platform.booking.interfaces.rest.transform;

import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.ParkingListingResource;

import com.acme.parkshare.platform.booking.interfaces.rest.resources.ListingAvailabilityPeriodResource;
import com.acme.parkshare.platform.shared.interfaces.rest.transform.JsonText;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class ParkingListingResourceMapper {
    private ParkingListingResourceMapper() {}

    public static ParkingListingResource toResource(ParkingListing listing) {
        return new ParkingListingResource(
                listing.getId(), listing.getOwnerId(), listing.getName(),
                listing.getAddress(), listing.getDistrict(), listing.getHourlyRate(),
                listing.isCovered(), listing.getRating(), listing.getReviewCount(),
                listing.getAvailability(), listing.getLatitude(), listing.getLongitude(),
                listing.getCreatedAt(), listing.getUpdatedAt(),
                JsonText.toStrings(listing.getFeatures()),
                JsonText.toStrings(listing.getPhotos()),
                listing.getPublicationStatus(),
                JsonText.toList(listing.getAvailabilityPeriods(),
                        new TypeReference<List<ListingAvailabilityPeriodResource>>() {})
        );
    }
}
