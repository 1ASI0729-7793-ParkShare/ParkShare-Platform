package com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates;

import com.acme.parkshare.platform.reputationnotifications.domain.model.events.NotificationCreatedEvent;
import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;

public final class Notification extends AbstractDomainAggregateRoot {

    private final Long id;
    private final Long recipientId;
    private final String type;
    private final String title;
    private final String message;
    private final Instant createdAt;
    private final Instant readAt;
    private final String relatedEntityType;
    private final Long relatedEntityId;
    private final Instant updatedAt;

    private Notification(Long id, Long recipientId, String type, String title, String message,
                         Instant createdAt, Instant readAt, String relatedEntityType,
                         Long relatedEntityId, Instant updatedAt) {
        this.id = id;
        this.recipientId = recipientId;
        this.type = type;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.readAt = readAt;
        this.relatedEntityType = relatedEntityType;
        this.relatedEntityId = relatedEntityId;
        this.updatedAt = updatedAt;
    }

    public static Notification create(Long recipientId, String type, String title, String message,
                                      String relatedEntityType, Long relatedEntityId, Instant now) {
        var notif = new Notification(null, recipientId, type, title, message,
                now, null, relatedEntityType, relatedEntityId, now);
        notif.registerEvent(new NotificationCreatedEvent(null, recipientId, now));
        return notif;
    }

    public static Notification rehydrate(Long id, Long recipientId, String type, String title, String message,
                                         Instant createdAt, Instant readAt, String relatedEntityType,
                                         Long relatedEntityId, Instant updatedAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid notification id");
        return new Notification(id, recipientId, type, title, message,
                createdAt, readAt, relatedEntityType, relatedEntityId, updatedAt);
    }

    public Notification markAsRead(Instant now) {
        return new Notification(this.id, this.recipientId, this.type, this.title, this.message,
                this.createdAt, now, this.relatedEntityType, this.relatedEntityId, now);
    }

    public Long getId() { return id; }
    public Long getRecipientId() { return recipientId; }
    public String getType() { return type; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getReadAt() { return readAt; }
    public String getRelatedEntityType() { return relatedEntityType; }
    public Long getRelatedEntityId() { return relatedEntityId; }
    public Instant getUpdatedAt() { return updatedAt; }
}
