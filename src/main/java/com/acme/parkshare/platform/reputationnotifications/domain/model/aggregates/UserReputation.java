package com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates;

import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

public final class UserReputation extends AbstractDomainAggregateRoot {

    private final Long id;
    private final Long userId;
    private final BigDecimal averageRating;
    private final int totalRatings;
    private final Instant createdAt;
    private final Instant updatedAt;

    private UserReputation(Long id, Long userId, BigDecimal averageRating, int totalRatings,
                           Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.averageRating = averageRating;
        this.totalRatings = totalRatings;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserReputation create(Long userId, Instant now) {
        return new UserReputation(null, userId, BigDecimal.ZERO, 0, now, now);
    }

    public static UserReputation rehydrate(Long id, Long userId, BigDecimal averageRating, int totalRatings,
                                          Instant createdAt, Instant updatedAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid user reputation id");
        return new UserReputation(id, userId, averageRating, totalRatings, createdAt, updatedAt);
    }

    public UserReputation addRating(int score, Instant now) {
        int newTotal = this.totalRatings + 1;
        BigDecimal totalSum = this.averageRating.multiply(BigDecimal.valueOf(this.totalRatings))
                .add(BigDecimal.valueOf(score));
        BigDecimal newAvg = totalSum.divide(BigDecimal.valueOf(newTotal), 2, RoundingMode.HALF_UP);
        return new UserReputation(this.id, this.userId, newAvg, newTotal, this.createdAt, now);
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public BigDecimal getAverageRating() { return averageRating; }
    public int getTotalRatings() { return totalRatings; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
