package com.acme.parkshare.platform.booking.domain.model.events;

import com.acme.parkshare.platform.shared.domain.events.DomainEvent;

import java.math.BigDecimal;
import java.time.Instant;

/** A reservation was completed: the owner earned {@code totalCost} for the stay. */
public record ReservationCompletedEvent(
        Long reservationId,
        Long ownerId,
        String driverName,
        String spaceName,
        Instant startedAt,
        Instant endedAt,
        int plannedHours,
        BigDecimal totalCost,
        Instant occurredOn) implements DomainEvent {}
