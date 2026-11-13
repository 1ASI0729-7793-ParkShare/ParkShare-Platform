package com.acme.parkshare.platform.booking.domain.model.aggregates;

import com.acme.parkshare.platform.booking.domain.model.events.ParkingListingCreatedEvent;
import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;

public final class ParkingListing extends AbstractDomainAggregateRoot {

    private final Long id;
    private final Long ownerId;
    private final String name;
    private final String address;
    private final String district;
    private final BigDecimal hourlyRate;
    private final boolean covered;
    private final BigDecimal rating;
    private final int reviewCount;
    private final String availability;
    private final Double latitude;
    private final Double longitude;
    private final Instant createdAt;
    private final Instant updatedAt;

    private ParkingListing(Long id, Long ownerId, String name, String address, String district,
                           BigDecimal hourlyRate, boolean covered, BigDecimal rating, int reviewCount,
                           String availability, Double latitude, Double longitude,
                           Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.district = district;
        this.hourlyRate = hourlyRate;
        this.covered = covered;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.availability = availability;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ParkingListing create(Long ownerId, String name, String address, String district,
                                        BigDecimal hourlyRate, boolean covered,
                                        Double latitude, Double longitude, Instant now) {
        var listing = new ParkingListing(null, ownerId, name, address, district,
                hourlyRate, covered, BigDecimal.ZERO, 0, "available",
                latitude, longitude, now, now);
        listing.registerEvent(new ParkingListingCreatedEvent(ownerId, now));
        return listing;
    }

    public static ParkingListing rehydrate(Long id, Long ownerId, String name, String address, String district,
                                           BigDecimal hourlyRate, boolean covered, BigDecimal rating,
                                           int reviewCount, String availability,
                                           Double latitude, Double longitude,
                                           Instant createdAt, Instant updatedAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid id");
        return new ParkingListing(id, ownerId, name, address, district,
                hourlyRate, covered, rating, reviewCount, availability,
                latitude, longitude, createdAt, updatedAt);
    }

    public Long getId() { return id; }
    public Long getOwnerId() { return ownerId; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getDistrict() { return district; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public boolean isCovered() { return covered; }
    public BigDecimal getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
    public String getAvailability() { return availability; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
