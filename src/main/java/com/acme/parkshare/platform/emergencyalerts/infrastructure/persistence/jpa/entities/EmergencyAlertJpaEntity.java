package com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "emergency_alert")
public class EmergencyAlertJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "parking_listing_id")
    private Long parkingListingId;

    @Column(name = "space_name", length = 200)
    private String spaceName;

    @Column(nullable = false, length = 30)
    private String kind;

    @Column(length = 500)
    private String notes;

    private Double latitude;

    private Double longitude;

    @Column(name = "location_source", length = 20)
    private String locationSource;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected EmergencyAlertJpaEntity() {}

    public EmergencyAlertJpaEntity(Long id, Long ownerId, Long parkingListingId, String spaceName, String kind,
                                   String notes, Double latitude, Double longitude, String locationSource,
                                   String status, Instant createdAt, Instant resolvedAt, Instant updatedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.parkingListingId = parkingListingId;
        this.spaceName = spaceName;
        this.kind = kind;
        this.notes = notes;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationSource = locationSource;
        this.status = status;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getOwnerId() { return ownerId; }
    public Long getParkingListingId() { return parkingListingId; }
    public String getSpaceName() { return spaceName; }
    public String getKind() { return kind; }
    public String getNotes() { return notes; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public String getLocationSource() { return locationSource; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getResolvedAt() { return resolvedAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
