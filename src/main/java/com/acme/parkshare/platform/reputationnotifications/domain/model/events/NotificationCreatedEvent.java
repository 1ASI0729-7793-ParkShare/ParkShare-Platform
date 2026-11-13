package com.acme.parkshare.platform.reputationnotifications.domain.model.events;

import com.acme.parkshare.platform.shared.domain.events.DomainEvent;
import java.time.Instant;

public record NotificationCreatedEvent(Long notificationId, Long recipientId, Instant occurredAt) implements DomainEvent {}
