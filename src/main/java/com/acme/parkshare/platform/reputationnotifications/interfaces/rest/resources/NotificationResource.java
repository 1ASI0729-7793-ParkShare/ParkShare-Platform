package com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources;

import java.time.Instant;

public record NotificationResource(
        Long id,
        Long recipientId,
        String type,
        String title,
        String message,
        Instant createdAt,
        Instant readAt,
        String relatedEntityType,
        Long relatedEntityId
) {}
