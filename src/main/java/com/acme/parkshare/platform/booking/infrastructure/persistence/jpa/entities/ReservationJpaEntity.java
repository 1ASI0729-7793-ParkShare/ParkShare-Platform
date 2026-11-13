package com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "reservation")
public class ReservationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parking_listing_id", nullable = false)
    private Long parkingListingId;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "driver_id", nullable = false)
    private Long driverId;

    @Column(name = "driver_name", nullable = false, length = 200)
    private String driverName;

    @Column(name = "vehicle_plate", nullable = false, length = 20)
    private String vehiclePlate;

    @Column(name = "vehicle_model", nullable = false, length = 100)
    private String vehicleModel;

    @Column(name = "space_name", nullable = false, length = 200)
    private String spaceName;

    @Column(name = "space_address", nullable = false, length = 500)
    private String spaceAddress;

    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Column(name = "planned_entry", nullable = false)
    private Instant plannedEntry;

    @Column(name = "planned_hours", nullable = false)
    private int plannedHours;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected ReservationJpaEntity() {}

    public ReservationJpaEntity(Long id, Long parkingListingId, Long ownerId, Long driverId,
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
