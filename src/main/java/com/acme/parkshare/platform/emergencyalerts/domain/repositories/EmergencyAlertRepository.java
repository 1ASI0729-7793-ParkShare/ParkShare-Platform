package com.acme.parkshare.platform.emergencyalerts.domain.repositories;

import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;
import java.util.List;
import java.util.Optional;

public interface EmergencyAlertRepository {
    EmergencyAlert save(EmergencyAlert alert);
    Optional<EmergencyAlert> findById(Long id);
    List<EmergencyAlert> findAll();
    List<EmergencyAlert> findAllByOwnerId(Long ownerId);
}
