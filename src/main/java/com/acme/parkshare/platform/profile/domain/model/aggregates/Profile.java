package com.acme.parkshare.platform.profile.domain.model.aggregates;

import com.acme.parkshare.platform.profile.domain.model.events.ProfileCreatedEvent;
import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;

public final class Profile extends AbstractDomainAggregateRoot {

    private final Long id;
    private final String fullName;
    private final String plate;
    private final String vehicleModel;
    private final String vehicleType;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Profile(Long id, String fullName, String plate, String vehicleModel,
                    String vehicleType, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.plate = plate;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Profile create(String fullName, String plate, String vehicleModel,
                                 String vehicleType, Instant now) {
        var profile = new Profile(null, fullName, plate, vehicleModel, vehicleType, now, now);
        profile.registerEvent(new ProfileCreatedEvent(fullName, now));
        return profile;
    }

    public static Profile rehydrate(Long id, String fullName, String plate, String vehicleModel,
                                    String vehicleType, Instant createdAt, Instant updatedAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid id");
        return new Profile(id, fullName, plate, vehicleModel, vehicleType, createdAt, updatedAt);
    }

    public Profile withUpdated(String fullName, String plate, String vehicleModel,
                               String vehicleType, Instant updatedAt) {
        return new Profile(this.id, fullName, plate, vehicleModel, vehicleType, this.createdAt, updatedAt);
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getPlate() { return plate; }
    public String getVehicleModel() { return vehicleModel; }
    public String getVehicleType() { return vehicleType; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
