package com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.UserReputation;
import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.entities.UserReputationJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserReputationPersistenceMapper {

    public UserReputationJpaEntity toEntity(UserReputation r) {
        return new UserReputationJpaEntity(
                r.getId(),
                r.getUserId(),
                r.getAverageRating(),
                r.getTotalRatings(),
                r.getCreatedAt(),
                r.getUpdatedAt()
        );
    }

    public UserReputation toDomain(UserReputationJpaEntity entity) {
        return UserReputation.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getAverageRating(),
                entity.getTotalRatings(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
