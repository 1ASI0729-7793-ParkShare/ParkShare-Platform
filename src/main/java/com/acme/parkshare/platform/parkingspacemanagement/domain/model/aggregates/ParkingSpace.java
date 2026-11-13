package com.acme.parkshare.platform.parkingspacemanagement.domain.model.aggregates;

import com.acme.parkshare.platform.parkingspacemanagement.domain.model.events.ParkingSpaceCreatedEvent;
import com.acme.parkshare.platform.parkingspacemanagement.domain.model.valueobjects.AvailabilityPeriod;
import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class ParkingSpace extends AbstractDomainAggregateRoot {

    private final Long id;
    private final Long ownerId;
    private final String address;
    private final String name;
    private final BigDecimal hourlyRate;
    private final String features;
    private final String publicationStatus;
    private final BigDecimal accumulatedIncome;
    private final List<AvailabilityPeriod> availabilityPeriods;
    private final Instant createdAt;
    private final Instant updatedAt;

    private ParkingSpace(Long id, Long ownerId, String address, String name,
                         BigDecimal hourlyRate, String features, String publicationStatus,
                         BigDecimal accumulatedIncome, List<AvailabilityPeriod> availabilityPeriods,
                         Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.address = address;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.features = features;
        this.publicationStatus = publicationStatus;
        this.accumulatedIncome = accumulatedIncome;
        this.availabilityPeriods = availabilityPeriods == null ? List.of() : List.copyOf(availabilityPeriods);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ParkingSpace create(Long ownerId, String address, String name,
                                      BigDecimal hourlyRate, String features,
                                      List<AvailabilityPeriod> availabilityPeriods, Instant now) {
        var space = new ParkingSpace(null, ownerId, address, name, hourlyRate, features,
                "paused", BigDecimal.ZERO, availabilityPeriods, now, now);
        space.registerEvent(new ParkingSpaceCreatedEvent(ownerId, now));
        return space;
    }

    public static ParkingSpace rehydrate(Long id, Long ownerId, String address, String name,
                                         BigDecimal hourlyRate, String features,
                                         String publicationStatus, BigDecimal accumulatedIncome,
                                         List<AvailabilityPeriod> availabilityPeriods,
                                         Instant createdAt, Instant updatedAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid id");
        return new ParkingSpace(id, ownerId, address, name, hourlyRate, features,
                publicationStatus, accumulatedIncome, availabilityPeriods, createdAt, updatedAt);
    }

    public ParkingSpace publish(Instant now) {
        return new ParkingSpace(this.id, this.ownerId, this.address, this.name,
                this.hourlyRate, this.features, "published", this.accumulatedIncome,
                this.availabilityPeriods, this.createdAt, now);
    }

    public ParkingSpace pause(Instant now) {
        return new ParkingSpace(this.id, this.ownerId, this.address, this.name,
                this.hourlyRate, this.features, "paused", this.accumulatedIncome,
                this.availabilityPeriods, this.createdAt, now);
    }

    public ParkingSpace addIncome(BigDecimal amount, Instant now) {
        return new ParkingSpace(this.id, this.ownerId, this.address, this.name,
                this.hourlyRate, this.features, this.publicationStatus,
                this.accumulatedIncome.add(amount), this.availabilityPeriods, this.createdAt, now);
    }

    public Long getId() { return id; }
    public Long getOwnerId() { return ownerId; }
    public String getAddress() { return address; }
    public String getName() { return name; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public String getFeatures() { return features; }
    public String getPublicationStatus() { return publicationStatus; }
    public BigDecimal getAccumulatedIncome() { return accumulatedIncome; }
    public List<AvailabilityPeriod> getAvailabilityPeriods() { return availabilityPeriods; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
