package com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument;
import com.acme.parkshare.platform.profile.domain.repositories.VerificationDocumentRepository;
import com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.VerificationDocumentJpaRepository;
import com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.mappers.VerificationDocumentPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VerificationDocumentRepositoryAdapter implements VerificationDocumentRepository {
    private final VerificationDocumentJpaRepository jpaRepository;
    private final VerificationDocumentPersistenceMapper mapper;

    public VerificationDocumentRepositoryAdapter(VerificationDocumentJpaRepository jpaRepository,
                                                  VerificationDocumentPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository; this.mapper = mapper;
    }

    @Override public VerificationDocument save(VerificationDocument d) { return mapper.toDomain(jpaRepository.save(mapper.toEntity(d))); }
    @Override public Optional<VerificationDocument> findById(Long id) { return jpaRepository.findById(id).map(mapper::toDomain); }
    @Override public List<VerificationDocument> findAll() { return jpaRepository.findAll().stream().map(mapper::toDomain).toList(); }
    @Override public List<VerificationDocument> findAllByProfileId(Long profileId) { return jpaRepository.findAllByProfileId(profileId).stream().map(mapper::toDomain).toList(); }
}
