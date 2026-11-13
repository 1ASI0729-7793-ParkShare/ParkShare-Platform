package com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.entities.UserReputationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserReputationJpaRepository extends JpaRepository<UserReputationJpaEntity, Long> {
    Optional<UserReputationJpaEntity> findByUserId(Long userId);
}
