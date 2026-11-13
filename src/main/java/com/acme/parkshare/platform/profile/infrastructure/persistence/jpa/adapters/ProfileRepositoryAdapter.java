package com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.profile.domain.model.aggregates.Profile;
import com.acme.parkshare.platform.profile.domain.repositories.ProfileRepository;
import com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.ProfileJpaRepository;
import com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.mappers.ProfilePersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProfileRepositoryAdapter implements ProfileRepository {
    private final ProfileJpaRepository jpaRepository;
    private final ProfilePersistenceMapper mapper;

    public ProfileRepositoryAdapter(ProfileJpaRepository jpaRepository, ProfilePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository; this.mapper = mapper;
    }

    @Override public Profile save(Profile p) { return mapper.toDomain(jpaRepository.save(mapper.toEntity(p))); }
    @Override public Optional<Profile> findById(Long id) { return jpaRepository.findById(id).map(mapper::toDomain); }
    @Override public List<Profile> findAll() { return jpaRepository.findAll().stream().map(mapper::toDomain).toList(); }
}
