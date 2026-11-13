package com.acme.parkshare.platform.booking.domain.model.events;

import com.acme.parkshare.platform.shared.domain.events.DomainEvent;

import java.time.Instant;

public record ParkingListingCreatedEvent(Long ownerId, Instant occurredOn) implements DomainEvent {}
