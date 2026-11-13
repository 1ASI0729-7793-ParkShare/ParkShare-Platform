package com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "emergency_alert")
public class EmergencyAlertJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id", nullable = false)
    private Long reporterId;

    @Column(name = "parking_listing_id", nullable = false)
    private Long parkingListingId;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected EmergencyAlertJpaEntity() {}

    public EmergencyAlertJpaEntity(Long id, Long reporterId, Long parkingListingId, Long ownerId,
                                   Double latitude, Double longitude, String description, String status,
                                   Instant createdAt, Instant resolvedAt, Instant updatedAt) {
        this.id = id;
        this.reporterId = reporterId;
        this.parkingListingId = parkingListingId;
        this.ownerId = ownerId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getReporterId() { return reporterId; }
    public Long getParkingListingId() { return parkingListingId; }
    public Long getOwnerId() { return ownerId; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getResolvedAt() { return resolvedAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
