package com.acme.parkshare.platform.payments.domain.repositories;

import com.acme.parkshare.platform.payments.domain.model.aggregates.OwnerPayout;

import java.util.Optional;

public interface OwnerPayoutRepository {
    Optional<OwnerPayout> findById(Long id);
    Optional<OwnerPayout> findByOwnerId(Long ownerId);
    OwnerPayout save(OwnerPayout payout);
}
