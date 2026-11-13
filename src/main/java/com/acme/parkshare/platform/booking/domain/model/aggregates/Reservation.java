package com.acme.parkshare.platform.booking.domain.model.aggregates;

import com.acme.parkshare.platform.booking.domain.model.events.ReservationCreatedEvent;
import com.acme.parkshare.platform.shared.domain.aggregates.AbstractDomainAggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;

public final class Reservation extends AbstractDomainAggregateRoot {

    private final Long id;
    private final Long parkingListingId;
    private final Long ownerId;
    private final Long driverId;
    private final String driverName;
    private final String vehiclePlate;
    private final String vehicleModel;
    private final String spaceName;
    private final String spaceAddress;
    private final BigDecimal hourlyRate;
    private final Instant plannedEntry;
    private final int plannedHours;
    private final String status;
    private final Instant startedAt;
    private final Instant endedAt;
    private final BigDecimal totalCost;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Reservation(Long id, Long parkingListingId, Long ownerId, Long driverId,
                        String driverName, String vehiclePlate, String vehicleModel,
                        String spaceName, String spaceAddress, BigDecimal hourlyRate,
                        Instant plannedEntry, int plannedHours, String status,
                        Instant startedAt, Instant endedAt, BigDecimal totalCost,
                        Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.parkingListingId = parkingListingId;
        this.ownerId = ownerId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.vehiclePlate = vehiclePlate;
        this.vehicleModel = vehicleModel;
        this.spaceName = spaceName;
        this.spaceAddress = spaceAddress;
        this.hourlyRate = hourlyRate;
        this.plannedEntry = plannedEntry;
        this.plannedHours = plannedHours;
        this.status = status;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.totalCost = totalCost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Reservation create(Long parkingListingId, Long ownerId, Long driverId,
                                     String driverName, String vehiclePlate, String vehicleModel,
                                     String spaceName, String spaceAddress, BigDecimal hourlyRate,
                                     Instant plannedEntry, int plannedHours, Instant now) {
        var reservation = new Reservation(null, parkingListingId, ownerId, driverId,
                driverName, vehiclePlate, vehicleModel, spaceName, spaceAddress,
                hourlyRate, plannedEntry, plannedHours, "requested",
                null, null, null, now, now);
        reservation.registerEvent(new ReservationCreatedEvent(driverId, parkingListingId, now));
        return reservation;
    }

    public static Reservation rehydrate(Long id, Long parkingListingId, Long ownerId, Long driverId,
                                        String driverName, String vehiclePlate, String vehicleModel,
                                        String spaceName, String spaceAddress, BigDecimal hourlyRate,
                                        Instant plannedEntry, int plannedHours, String status,
                                        Instant startedAt, Instant endedAt, BigDecimal totalCost,
                                        Instant createdAt, Instant updatedAt) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid id");
        return new Reservation(id, parkingListingId, ownerId, driverId,
                driverName, vehiclePlate, vehicleModel, spaceName, spaceAddress,
                hourlyRate, plannedEntry, plannedHours, status,
                startedAt, endedAt, totalCost, createdAt, updatedAt);
    }

    public Reservation confirm(Instant now) {
        return new Reservation(id, parkingListingId, ownerId, driverId,
                driverName, vehiclePlate, vehicleModel, spaceName, spaceAddress,
                hourlyRate, plannedEntry, plannedHours, "confirmed",
                startedAt, endedAt, totalCost, createdAt, now);
    }

    public Reservation reject(Instant now) {
        return new Reservation(id, parkingListingId, ownerId, driverId,
                driverName, vehiclePlate, vehicleModel, spaceName, spaceAddress,
                hourlyRate, plannedEntry, plannedHours, "rejected",
                startedAt, endedAt, totalCost, createdAt, now);
    }

    public Reservation start(Instant now) {
        return new Reservation(id, parkingListingId, ownerId, driverId,
                driverName, vehiclePlate, vehicleModel, spaceName, spaceAddress,
                hourlyRate, plannedEntry, plannedHours, "in-use",
                now, endedAt, totalCost, createdAt, now);
    }

    public Reservation finish(Instant now) {
        BigDecimal cost = hourlyRate.multiply(BigDecimal.valueOf(plannedHours));
        return new Reservation(id, parkingListingId, ownerId, driverId,
                driverName, vehiclePlate, vehicleModel, spaceName, spaceAddress,
                hourlyRate, plannedEntry, plannedHours, "completed",
                startedAt, now, cost, createdAt, now);
    }

    public Long getId() { return id; }
    public Long getParkingListingId() { return parkingListingId; }
    public Long getOwnerId() { return ownerId; }
    public Long getDriverId() { return driverId; }
    public String getDriverName() { return driverName; }
    public String getVehiclePlate() { return vehiclePlate; }
    public String getVehicleModel() { return vehicleModel; }
    public String getSpaceName() { return spaceName; }
    public String getSpaceAddress() { return spaceAddress; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public Instant getPlannedEntry() { return plannedEntry; }
    public int getPlannedHours() { return plannedHours; }
    public String getStatus() { return status; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getEndedAt() { return endedAt; }
    public BigDecimal getTotalCost() { return totalCost; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
