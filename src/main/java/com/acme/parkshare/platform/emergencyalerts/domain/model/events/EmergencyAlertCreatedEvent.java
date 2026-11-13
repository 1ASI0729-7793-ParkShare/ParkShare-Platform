package com.acme.parkshare.platform.emergencyalerts.domain.model.events;

import com.acme.parkshare.platform.shared.domain.events.DomainEvent;
import java.time.Instant;

public record EmergencyAlertCreatedEvent(Long alertId, Long reporterId, Long parkingListingId, Instant occurredAt) implements DomainEvent {}
