package com.acme.parkshare.platform.payments.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.payments.infrastructure.persistence.jpa.entities.OwnerPayoutJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerPayoutJpaRepository extends JpaRepository<OwnerPayoutJpaEntity, Long> {
    Optional<OwnerPayoutJpaEntity> findByOwnerId(Long ownerId);
}
