package com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "profile")
public class ProfileJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;
    @Column(nullable = false, length = 20)
    private String plate;
    @Column(name = "vehicle_model", nullable = false, length = 100)
    private String vehicleModel;
    @Column(name = "vehicle_type", nullable = false, length = 20)
    private String vehicleType;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected ProfileJpaEntity() {}

    public ProfileJpaEntity(Long id, String fullName, String plate, String vehicleModel,
                            String vehicleType, Instant createdAt, Instant updatedAt) {
        this.id = id; this.fullName = fullName; this.plate = plate;
        this.vehicleModel = vehicleModel; this.vehicleType = vehicleType;
        this.createdAt = createdAt; this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getPlate() { return plate; }
    public String getVehicleModel() { return vehicleModel; }
    public String getVehicleType() { return vehicleType; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
