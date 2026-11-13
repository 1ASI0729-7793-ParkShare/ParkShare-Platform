package com.acme.parkshare.platform.reputationnotifications.interfaces.rest.transform;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Notification;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources.NotificationResource;

public class NotificationResourceMapper {
    private NotificationResourceMapper() {}

    public static NotificationResource toResource(Notification n) {
        return new NotificationResource(
                n.getId(),
                n.getRecipientId(),
                n.getType(),
                n.getTitle(),
                n.getMessage(),
                n.getCreatedAt(),
                n.getReadAt(),
                n.getRelatedEntityType(),
                n.getRelatedEntityId()
        );
    }
}
