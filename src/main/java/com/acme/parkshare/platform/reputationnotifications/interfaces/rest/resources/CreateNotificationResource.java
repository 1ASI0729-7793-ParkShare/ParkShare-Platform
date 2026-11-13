package com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNotificationResource(
        @NotNull Long recipientId,
        @NotBlank String type,
        @NotBlank String title,
        @NotBlank String message,
        String relatedEntityType,
        Long relatedEntityId
) {}
