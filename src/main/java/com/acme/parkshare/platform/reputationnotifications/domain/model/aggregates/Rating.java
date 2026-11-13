package com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates;

import com.acme.parkshare.platform.reputationnotifications.domain.model.events.RatingCreatedEvent;
import com.acme.parkshare.platform.reputationnotifications.domain.model.valueobjects.RatingScore;
import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;

public final class Rating extends AbstractDomainAggregateRoot {

    private final Long id;
    private final Long bookingId;
    private final Long reviewerId;
    private final Long revieweeId;
    private final RatingScore score;
    private final String comment;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Rating(Long id, Long bookingId, Long reviewerId, Long revieweeId,
                   RatingScore score, String comment, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.reviewerId = reviewerId;
        this.revieweeId = revieweeId;
        this.score = score;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Rating create(Long bookingId, Long reviewerId, Long revieweeId,
                                int score, String comment, Instant now) {
        var rating = new Rating(null, bookingId, reviewerId, revieweeId,
                new RatingScore(score), comment, now, now);
        rating.registerEvent(new RatingCreatedEvent(null, revieweeId, score, now));
        return rating;
    }

    public static Rating rehydrate(Long id, Long bookingId, Long reviewerId, Long revieweeId,
                                   int score, String comment, Instant createdAt, Instant updatedAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid rating id");
        return new Rating(id, bookingId, reviewerId, revieweeId,
                new RatingScore(score), comment, createdAt, updatedAt);
    }

    public Long getId() { return id; }
    public Long getBookingId() { return bookingId; }
    public Long getReviewerId() { return reviewerId; }
    public Long getRevieweeId() { return revieweeId; }
    public RatingScore getScore() { return score; }
    public String getComment() { return comment; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
