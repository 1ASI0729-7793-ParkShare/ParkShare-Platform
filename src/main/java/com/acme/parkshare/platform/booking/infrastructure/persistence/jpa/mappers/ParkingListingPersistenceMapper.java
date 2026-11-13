package com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;
import com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.entities.ParkingListingJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ParkingListingPersistenceMapper {

    public ParkingListingJpaEntity toEntity(ParkingListing listing) {
        return new ParkingListingJpaEntity(
                listing.getId(), listing.getOwnerId(), listing.getName(),
                listing.getAddress(), listing.getDistrict(), listing.getHourlyRate(),
                listing.isCovered(), listing.getRating(), listing.getReviewCount(),
                listing.getAvailability(), listing.getLatitude(), listing.getLongitude(),
                listing.getCreatedAt(), listing.getUpdatedAt()
        );
    }

    public ParkingListing toDomain(ParkingListingJpaEntity e) {
        return ParkingListing.rehydrate(
                e.getId(), e.getOwnerId(), e.getName(), e.getAddress(), e.getDistrict(),
                e.getHourlyRate(), e.isCovered(), e.getRating(), e.getReviewCount(),
                e.getAvailability(), e.getLatitude(), e.getLongitude(),
                e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
