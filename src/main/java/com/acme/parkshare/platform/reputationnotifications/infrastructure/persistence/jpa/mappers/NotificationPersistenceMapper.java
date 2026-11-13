package com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Notification;
import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.entities.NotificationJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationPersistenceMapper {

    public NotificationJpaEntity toEntity(Notification n) {
        return new NotificationJpaEntity(
                n.getId(),
                n.getRecipientId(),
                n.getType(),
                n.getTitle(),
                n.getMessage(),
                n.getCreatedAt(),
                n.getReadAt(),
                n.getRelatedEntityType(),
                n.getRelatedEntityId(),
                n.getUpdatedAt()
        );
    }

    public Notification toDomain(NotificationJpaEntity entity) {
        return Notification.rehydrate(
                entity.getId(),
                entity.getRecipientId(),
                entity.getType(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getCreatedAt(),
                entity.getReadAt(),
                entity.getRelatedEntityType(),
                entity.getRelatedEntityId(),
                entity.getUpdatedAt()
        );
    }
}
