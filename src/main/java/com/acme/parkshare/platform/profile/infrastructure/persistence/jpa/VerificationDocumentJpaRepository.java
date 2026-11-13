package com.acme.parkshare.platform.profile.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.entities.VerificationDocumentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationDocumentJpaRepository extends JpaRepository<VerificationDocumentJpaEntity, Long> {
    List<VerificationDocumentJpaEntity> findAllByProfileId(Long profileId);
}
