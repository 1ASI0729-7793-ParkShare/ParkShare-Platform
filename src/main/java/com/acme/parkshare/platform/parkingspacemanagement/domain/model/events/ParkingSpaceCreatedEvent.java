package com.acme.parkshare.platform.parkingspacemanagement.domain.model.events;

import com.acme.parkshare.platform.shared.domain.events.DomainEvent;

import java.time.Instant;

public record ParkingSpaceCreatedEvent(Long ownerId, Instant occurredOn) implements DomainEvent {}
