package com.acme.parkshare.platform.reputationnotifications.application.commands;

public record CreateNotificationCommand(
        Long recipientId,
        String type,
        String title,
        String message,
        String relatedEntityType,
        Long relatedEntityId
) {}
