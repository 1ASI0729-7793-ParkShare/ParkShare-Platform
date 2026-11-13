package com.acme.parkshare.platform.incidentreporting.domain.model.aggregates;

import com.acme.parkshare.platform.incidentreporting.domain.model.events.IncidentCreatedEvent;
import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;

public final class Incident extends AbstractDomainAggregateRoot {

    private final Long id;
    private final Long reporterId;
    private final String reporterName;
    private final Long parkingListingId;
    private final Long ownerId;
    private final String spaceName;
    private final String spaceAddress;
    private final String incidentTypes;
    private final String description;
    private final String photos;
    private final String status;
    private final Instant createdAt;
    private final Instant resolvedAt;
    private final Instant updatedAt;

    private Incident(Long id, Long reporterId, String reporterName, Long parkingListingId, Long ownerId,
                     String spaceName, String spaceAddress, String incidentTypes, String description,
                     String photos, String status, Instant createdAt, Instant resolvedAt, Instant updatedAt) {
        this.id = id; this.reporterId = reporterId; this.reporterName = reporterName;
        this.parkingListingId = parkingListingId; this.ownerId = ownerId;
        this.spaceName = spaceName; this.spaceAddress = spaceAddress;
        this.incidentTypes = incidentTypes; this.description = description;
        this.photos = photos; this.status = status;
        this.createdAt = createdAt; this.resolvedAt = resolvedAt; this.updatedAt = updatedAt;
    }

    public static Incident create(Long reporterId, String reporterName, Long parkingListingId, Long ownerId,
                                   String spaceName, String spaceAddress, String incidentTypes,
                                   String description, String photos, Instant now) {
        if (description == null || description.trim().length() < 10 || description.trim().length() > 500) {
            throw new IllegalArgumentException("Description must be between 10 and 500 characters");
        }
        var incident = new Incident(null, reporterId, reporterName, parkingListingId, ownerId,
                spaceName, spaceAddress, incidentTypes, description.trim(), photos,
                "open", now, null, now);
        incident.registerEvent(new IncidentCreatedEvent(reporterId, parkingListingId, now));
        return incident;
    }

    public static Incident rehydrate(Long id, Long reporterId, String reporterName, Long parkingListingId,
                                      Long ownerId, String spaceName, String spaceAddress,
                                      String incidentTypes, String description, String photos,
                                      String status, Instant createdAt, Instant resolvedAt, Instant updatedAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid id");
        return new Incident(id, reporterId, reporterName, parkingListingId, ownerId,
                spaceName, spaceAddress, incidentTypes, description, photos,
                status, createdAt, resolvedAt, updatedAt);
    }

    public Incident startReview(Instant now) {
        return new Incident(id, reporterId, reporterName, parkingListingId, ownerId,
                spaceName, spaceAddress, incidentTypes, description, photos,
                "in-review", createdAt, resolvedAt, now);
    }

    public Incident resolve(Instant now) {
        return new Incident(id, reporterId, reporterName, parkingListingId, ownerId,
                spaceName, spaceAddress, incidentTypes, description, photos,
                "resolved", createdAt, now, now);
    }

    public Long getId() { return id; }
    public Long getReporterId() { return reporterId; }
    public String getReporterName() { return reporterName; }
    public Long getParkingListingId() { return parkingListingId; }
    public Long getOwnerId() { return ownerId; }
    public String getSpaceName() { return spaceName; }
    public String getSpaceAddress() { return spaceAddress; }
    public String getIncidentTypes() { return incidentTypes; }
    public String getDescription() { return description; }
    public String getPhotos() { return photos; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getResolvedAt() { return resolvedAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
