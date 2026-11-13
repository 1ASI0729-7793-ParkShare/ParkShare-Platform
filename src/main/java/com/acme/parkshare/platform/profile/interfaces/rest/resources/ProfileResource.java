package com.acme.parkshare.platform.profile.interfaces.rest.resources;

import java.time.Instant;

public record ProfileResource(Long id, String fullName, String plate, String vehicleModel,
                               String vehicleType, Instant createdAt, Instant updatedAt) {}
