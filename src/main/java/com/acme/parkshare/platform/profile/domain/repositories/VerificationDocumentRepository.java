package com.acme.parkshare.platform.profile.domain.repositories;

import com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument;

import java.util.List;
import java.util.Optional;

public interface VerificationDocumentRepository {
    VerificationDocument save(VerificationDocument document);
    Optional<VerificationDocument> findById(Long id);
    List<VerificationDocument> findAll();
    List<VerificationDocument> findAllByProfileId(Long profileId);
}
