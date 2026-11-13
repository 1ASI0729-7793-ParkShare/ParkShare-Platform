package com.acme.parkshare.platform.profile.domain.model.aggregates;

import com.acme.parkshare.platform.profile.domain.model.events.VerificationDocumentCreatedEvent;
import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;

public final class VerificationDocument extends AbstractDomainAggregateRoot {

    private final Long id;
    private final Long profileId;
    private final String type;
    private final String status;
    private final Instant updatedAt;
    private final String fileName;
    private final Instant createdAt;

    private VerificationDocument(Long id, Long profileId, String type, String status,
                                  Instant updatedAt, String fileName, Instant createdAt) {
        this.id = id;
        this.profileId = profileId;
        this.type = type;
        this.status = status;
        this.updatedAt = updatedAt;
        this.fileName = fileName;
        this.createdAt = createdAt;
    }

    public static VerificationDocument create(Long profileId, String type, Instant now) {
        var doc = new VerificationDocument(null, profileId, type, "in-review", now, null, now);
        doc.registerEvent(new VerificationDocumentCreatedEvent(profileId, type, now));
        return doc;
    }

    public static VerificationDocument rehydrate(Long id, Long profileId, String type, String status,
                                                  Instant updatedAt, String fileName, Instant createdAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid id");
        return new VerificationDocument(id, profileId, type, status, updatedAt, fileName, createdAt);
    }

    public VerificationDocument approve(Instant now) {
        return VerificationDocument.rehydrate(this.id, this.profileId, this.type, "verified", now, this.fileName, this.createdAt);
    }

    public VerificationDocument reject(Instant now) {
        return VerificationDocument.rehydrate(this.id, this.profileId, this.type, "rejected", now, this.fileName, this.createdAt);
    }

    public VerificationDocument review(Instant now) {
        return VerificationDocument.rehydrate(this.id, this.profileId, this.type, "in-review", now, this.fileName, this.createdAt);
    }

    public Long getId() { return id; }
    public Long getProfileId() { return profileId; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public Instant getUpdatedAt() { return updatedAt; }
    public String getFileName() { return fileName; }
    public Instant getCreatedAt() { return createdAt; }
}
