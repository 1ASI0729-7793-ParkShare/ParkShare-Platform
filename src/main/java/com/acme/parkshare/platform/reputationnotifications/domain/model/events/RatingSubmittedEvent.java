package com.acme.parkshare.platform.reputationnotifications.domain.model.events;

import com.acme.parkshare.platform.shared.domain.events.DomainEvent;

import java.time.Instant;

/** A rating was saved. Other contexts (e.g. Booking) react to keep their own read data up to date. */
public record RatingSubmittedEvent(
        Long ratingId,
        Long bookingId,
        Long reviewerId,
        Long revieweeId,
        int score,
        Instant occurredOn) implements DomainEvent {}
