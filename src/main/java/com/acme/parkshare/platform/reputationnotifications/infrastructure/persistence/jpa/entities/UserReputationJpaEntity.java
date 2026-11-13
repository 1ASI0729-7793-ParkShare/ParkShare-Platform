package com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "user_reputation")
public class UserReputationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "average_rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal averageRating;

    @Column(name = "total_ratings", nullable = false)
    private int totalRatings;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected UserReputationJpaEntity() {}

    public UserReputationJpaEntity(Long id, Long userId, BigDecimal averageRating, int totalRatings,
                                   Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.averageRating = averageRating;
        this.totalRatings = totalRatings;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public BigDecimal getAverageRating() { return averageRating; }
    public int getTotalRatings() { return totalRatings; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
