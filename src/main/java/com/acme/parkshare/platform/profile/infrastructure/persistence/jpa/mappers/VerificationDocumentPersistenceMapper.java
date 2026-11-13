package com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument;
import com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.entities.VerificationDocumentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class VerificationDocumentPersistenceMapper {
    public VerificationDocumentJpaEntity toEntity(VerificationDocument d) {
        return new VerificationDocumentJpaEntity(d.getId(), d.getProfileId(), d.getType(),
                d.getStatus(), d.getFileName(), d.getCreatedAt(), d.getUpdatedAt());
    }
    public VerificationDocument toDomain(VerificationDocumentJpaEntity e) {
        // rehydrate(id, profileId, type, status, updatedAt, fileName, createdAt)
        return VerificationDocument.rehydrate(e.getId(), e.getProfileId(), e.getType(),
                e.getStatus(), e.getUpdatedAt(), e.getFileName(), e.getCreatedAt());
    }
}
