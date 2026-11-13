package com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Rating;
import com.acme.parkshare.platform.reputationnotifications.domain.repositories.RatingRepository;
import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.RatingJpaRepository;
import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.mappers.RatingPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RatingRepositoryAdapter implements RatingRepository {

    private final RatingJpaRepository repository;
    private final RatingPersistenceMapper mapper;

    public RatingRepositoryAdapter(RatingJpaRepository repository, RatingPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Rating save(Rating rating) {
        return mapper.toDomain(repository.save(mapper.toEntity(rating)));
    }

    @Override
    public Optional<Rating> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Rating> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Rating> findAllByRevieweeId(Long revieweeId) {
        return repository.findAllByRevieweeId(revieweeId).stream().map(mapper::toDomain).toList();
    }
}
