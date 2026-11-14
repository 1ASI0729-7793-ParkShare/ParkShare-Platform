package com.acme.parkshare.platform.incidentreporting.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.Instant;

@Entity
@Table(name = "incident")
public class IncidentJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "reporter_id", nullable = false) private Long reporterId;
    @Column(name = "reporter_name", nullable = false, length = 200) private String reporterName;
    @Column(name = "parking_listing_id", nullable = false) private Long parkingListingId;
    @Column(name = "owner_id", nullable = false) private Long ownerId;
    @Column(name = "space_name", nullable = false, length = 200) private String spaceName;
    @Column(name = "space_address", nullable = false, length = 500) private String spaceAddress;
    @Column(name = "incident_types", nullable = false, length = 500) private String incidentTypes;
    @Column(nullable = false, length = 500) private String description;
    @JdbcTypeCode(SqlTypes.LONGVARCHAR) @Column private String photos;
    @Column(nullable = false, length = 20) private String status;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "resolved_at") private Instant resolvedAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;

    protected IncidentJpaEntity() {}

    public IncidentJpaEntity(Long id, Long reporterId, String reporterName, Long parkingListingId,
                              Long ownerId, String spaceName, String spaceAddress, String incidentTypes,
                              String description, String photos, String status,
                              Instant createdAt, Instant resolvedAt, Instant updatedAt) {
        this.id = id; this.reporterId = reporterId; this.reporterName = reporterName;
        this.parkingListingId = parkingListingId; this.ownerId = ownerId;
        this.spaceName = spaceName; this.spaceAddress = spaceAddress;
        this.incidentTypes = incidentTypes; this.description = description;
        this.photos = photos; this.status = status;
        this.createdAt = createdAt; this.resolvedAt = resolvedAt; this.updatedAt = updatedAt;
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
