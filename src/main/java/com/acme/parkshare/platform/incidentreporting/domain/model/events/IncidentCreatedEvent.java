package com.acme.parkshare.platform.incidentreporting.domain.model.events;

import com.acme.parkshare.platform.shared.domain.events.DomainEvent;
import java.time.Instant;

public record IncidentCreatedEvent(Long reporterId, Long parkingListingId, Instant occurredAt) implements DomainEvent {}
