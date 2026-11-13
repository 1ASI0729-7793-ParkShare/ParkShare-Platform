package com.acme.parkshare.platform.parkingspacemanagement.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_space")
public class ParkingSpaceJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Column(columnDefinition = "TEXT")
    private String features;

    @Column(name = "publication_status", nullable = false, length = 20)
    private String publicationStatus;

    @Column(name = "accumulated_income", nullable = false, precision = 10, scale = 2)
    private BigDecimal accumulatedIncome;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "parking_space_availability_period", joinColumns = @JoinColumn(name = "parking_space_id"))
    private List<AvailabilityPeriodEmbeddable> availabilityPeriods = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected ParkingSpaceJpaEntity() {}

    public ParkingSpaceJpaEntity(Long id, Long ownerId, String address, String name,
                                BigDecimal hourlyRate, String features, String publicationStatus,
                                BigDecimal accumulatedIncome,
                                List<AvailabilityPeriodEmbeddable> availabilityPeriods,
                                Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.address = address;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.features = features;
        this.publicationStatus = publicationStatus;
        this.accumulatedIncome = accumulatedIncome;
        this.availabilityPeriods = availabilityPeriods != null ? availabilityPeriods : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getOwnerId() { return ownerId; }
    public String getAddress() { return address; }
    public String getName() { return name; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public String getFeatures() { return features; }
    public String getPublicationStatus() { return publicationStatus; }
    public BigDecimal getAccumulatedIncome() { return accumulatedIncome; }
    public List<AvailabilityPeriodEmbeddable> getAvailabilityPeriods() { return availabilityPeriods; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
