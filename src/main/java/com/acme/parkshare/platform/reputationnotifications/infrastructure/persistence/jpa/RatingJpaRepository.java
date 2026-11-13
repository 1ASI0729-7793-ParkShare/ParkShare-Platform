package com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.entities.RatingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RatingJpaRepository extends JpaRepository<RatingJpaEntity, Long> {
    List<RatingJpaEntity> findAllByRevieweeId(Long revieweeId);
}
