package com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.UserReputation;
import com.acme.parkshare.platform.reputationnotifications.domain.repositories.UserReputationRepository;
import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.UserReputationJpaRepository;
import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.mappers.UserReputationPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserReputationRepositoryAdapter implements UserReputationRepository {

    private final UserReputationJpaRepository repository;
    private final UserReputationPersistenceMapper mapper;

    public UserReputationRepositoryAdapter(UserReputationJpaRepository repository, UserReputationPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserReputation save(UserReputation userReputation) {
        return mapper.toDomain(repository.save(mapper.toEntity(userReputation)));
    }

    @Override
    public Optional<UserReputation> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<UserReputation> findByUserId(Long userId) {
        return repository.findByUserId(userId).map(mapper::toDomain);
    }

    @Override
    public List<UserReputation> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
