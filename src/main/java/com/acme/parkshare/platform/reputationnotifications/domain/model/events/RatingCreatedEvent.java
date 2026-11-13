package com.acme.parkshare.platform.reputationnotifications.domain.model.events;

import com.acme.parkshare.platform.shared.domain.events.DomainEvent;
import java.time.Instant;

public record RatingCreatedEvent(Long ratingId, Long revieweeId, int score, Instant occurredAt) implements DomainEvent {}
