package com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "parking_listing")
public class ParkingListingJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Column(nullable = false)
    private boolean covered;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "review_count", nullable = false)
    private int reviewCount;

    @Column(nullable = false, length = 20)
    private String availability;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(length = 1000)
    private String features;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR) @Column
    private String photos;

    @Column(name = "publication_status", length = 20)
    private String publicationStatus;

    @Column(name = "availability_periods", columnDefinition = "TEXT")
    private String availabilityPeriods;

    protected ParkingListingJpaEntity() {}

    public ParkingListingJpaEntity(Long id, Long ownerId, String name, String address, String district,
                                   BigDecimal hourlyRate, boolean covered, BigDecimal rating, int reviewCount,
                                   String availability, Double latitude, Double longitude,
                                   Instant createdAt, Instant updatedAt,
                                   String features, String photos, String publicationStatus,
                                   String availabilityPeriods) {
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
        this.features = features;
        this.photos = photos;
        this.publicationStatus = publicationStatus;
        this.availabilityPeriods = availabilityPeriods;
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
    public String getFeatures() { return features; }
    public String getPhotos() { return photos; }
    public String getPublicationStatus() { return publicationStatus; }
    public String getAvailabilityPeriods() { return availabilityPeriods; }
}
