package com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates;

import com.acme.parkshare.platform.emergencyalerts.domain.model.events.EmergencyAlertCreatedEvent;
import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;

public final class EmergencyAlert extends AbstractDomainAggregateRoot {

    private final Long id;
    private final Long reporterId;
    private final Long parkingListingId;
    private final Long ownerId;
    private final Double latitude;
    private final Double longitude;
    private final String description;
    private final String status;
    private final Instant createdAt;
    private final Instant resolvedAt;
    private final Instant updatedAt;

    private EmergencyAlert(Long id, Long reporterId, Long parkingListingId, Long ownerId,
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

    public static EmergencyAlert create(Long reporterId, Long parkingListingId, Long ownerId,
                                        Double latitude, Double longitude, String description,
                                        Instant now) {
        var alert = new EmergencyAlert(null, reporterId, parkingListingId, ownerId,
                latitude, longitude, description, "active", now, null, now);
        alert.registerEvent(new EmergencyAlertCreatedEvent(null, reporterId, parkingListingId, now));
        return alert;
    }

    public static EmergencyAlert rehydrate(Long id, Long reporterId, Long parkingListingId, Long ownerId,
                                          Double latitude, Double longitude, String description, String status,
                                          Instant createdAt, Instant resolvedAt, Instant updatedAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid emergency alert id");
        return new EmergencyAlert(id, reporterId, parkingListingId, ownerId,
                latitude, longitude, description, status, createdAt, resolvedAt, updatedAt);
    }

    public EmergencyAlert resolve(Instant now) {
        return new EmergencyAlert(this.id, this.reporterId, this.parkingListingId, this.ownerId,
                this.latitude, this.longitude, this.description, "resolved",
                this.createdAt, now, now);
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
