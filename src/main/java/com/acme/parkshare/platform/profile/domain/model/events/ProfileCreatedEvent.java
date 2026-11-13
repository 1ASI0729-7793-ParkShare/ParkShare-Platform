package com.acme.parkshare.platform.profile.domain.model.events;

import com.acme.parkshare.platform.shared.domain.events.DomainEvent;

import java.time.Instant;

public record ProfileCreatedEvent(String fullName, Instant occurredAt) implements DomainEvent {}
