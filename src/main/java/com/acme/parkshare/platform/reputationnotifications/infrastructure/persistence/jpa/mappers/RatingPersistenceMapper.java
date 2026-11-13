package com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Rating;
import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.entities.RatingJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RatingPersistenceMapper {

    public RatingJpaEntity toEntity(Rating rating) {
        return new RatingJpaEntity(
                rating.getId(),
                rating.getBookingId(),
                rating.getReviewerId(),
                rating.getRevieweeId(),
                rating.getScore().value(),
                rating.getComment(),
                rating.getCreatedAt(),
                rating.getUpdatedAt()
        );
    }

    public Rating toDomain(RatingJpaEntity entity) {
        return Rating.rehydrate(
                entity.getId(),
                entity.getBookingId(),
                entity.getReviewerId(),
                entity.getRevieweeId(),
                entity.getScore(),
                entity.getComment(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
