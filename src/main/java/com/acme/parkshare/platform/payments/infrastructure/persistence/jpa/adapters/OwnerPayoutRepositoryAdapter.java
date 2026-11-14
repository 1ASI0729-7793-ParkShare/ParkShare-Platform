package com.acme.parkshare.platform.payments.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.payments.domain.model.aggregates.OwnerPayout;
import com.acme.parkshare.platform.payments.domain.repositories.OwnerPayoutRepository;
import com.acme.parkshare.platform.payments.infrastructure.persistence.jpa.OwnerPayoutJpaRepository;
import com.acme.parkshare.platform.payments.infrastructure.persistence.jpa.entities.OwnerPayoutJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OwnerPayoutRepositoryAdapter implements OwnerPayoutRepository {

    private final OwnerPayoutJpaRepository jpa;

    public OwnerPayoutRepositoryAdapter(OwnerPayoutJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<OwnerPayout> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<OwnerPayout> findByOwnerId(Long ownerId) {
        return jpa.findByOwnerId(ownerId).map(this::toDomain);
    }

    @Override
    public OwnerPayout save(OwnerPayout p) {
        return toDomain(jpa.save(new OwnerPayoutJpaEntity(p.getId(), p.getOwnerId(), p.getAvailableBalance(),
                p.getCompletedAmount(), p.getInTransitAmount(), p.getLinkedAccount(), p.getCommissionRate())));
    }

    private OwnerPayout toDomain(OwnerPayoutJpaEntity e) {
        return new OwnerPayout(e.getId(), e.getOwnerId(), e.getAvailableBalance(), e.getCompletedAmount(),
                e.getInTransitAmount(), e.getLinkedAccount(), e.getCommissionRate());
    }
}
