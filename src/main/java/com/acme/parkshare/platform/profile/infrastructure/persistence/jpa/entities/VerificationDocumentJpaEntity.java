package com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "verification_document")
public class VerificationDocumentJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "profile_id", nullable = false)
    private Long profileId;
    @Column(nullable = false, length = 50)
    private String type;
    @Column(nullable = false, length = 20)
    private String status;
    @Column(name = "file_name", length = 500)
    private String fileName;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected VerificationDocumentJpaEntity() {}

    public VerificationDocumentJpaEntity(Long id, Long profileId, String type, String status,
                                         String fileName, Instant createdAt, Instant updatedAt) {
        this.id = id; this.profileId = profileId; this.type = type;
        this.status = status; this.fileName = fileName;
        this.createdAt = createdAt; this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getProfileId() { return profileId; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public String getFileName() { return fileName; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
