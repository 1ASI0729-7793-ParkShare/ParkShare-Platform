package com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates;

import com.acme.parkshare.platform.emergencyalerts.domain.model.events.EmergencyAlertCreatedEvent;
import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;

/** An SOS raised by a parking-space owner (fire, theft, accident, ...). */
public final class EmergencyAlert extends AbstractDomainAggregateRoot {

    private final Long id;
    private final Long ownerId;
    private final Long parkingListingId;
    private final String spaceName;
    private final String kind;
    private final String notes;
    private final Double latitude;
    private final Double longitude;
    /** Where the coordinates came from: gps | space. */
    private final String locationSource;
    private final String status;
    private final Instant createdAt;
    private final Instant resolvedAt;
    private final Instant updatedAt;

    private EmergencyAlert(Long id, Long ownerId, Long parkingListingId, String spaceName, String kind,
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

    public static EmergencyAlert create(Long ownerId, Long parkingListingId, String spaceName, String kind,
                                        String notes, Double latitude, Double longitude,
                                        String locationSource, Instant now) {
        var alert = new EmergencyAlert(null, ownerId, parkingListingId, spaceName, kind, notes,
                latitude, longitude, locationSource, "active", now, null, now);
        alert.registerEvent(new EmergencyAlertCreatedEvent(null, ownerId, parkingListingId, now));
        return alert;
    }

    public static EmergencyAlert rehydrate(Long id, Long ownerId, Long parkingListingId, String spaceName,
                                           String kind, String notes, Double latitude, Double longitude,
                                           String locationSource, String status, Instant createdAt,
                                           Instant resolvedAt, Instant updatedAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid emergency alert id");
        return new EmergencyAlert(id, ownerId, parkingListingId, spaceName, kind, notes, latitude, longitude,
                locationSource, status, createdAt, resolvedAt, updatedAt);
    }

    public EmergencyAlert resolve(Instant now) {
        return new EmergencyAlert(id, ownerId, parkingListingId, spaceName, kind, notes, latitude, longitude,
                locationSource, "resolved", createdAt, now, now);
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
