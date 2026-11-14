package com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa.entities.EmergencyAlertJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyAlertJpaRepository extends JpaRepository<EmergencyAlertJpaEntity, Long> {
    List<EmergencyAlertJpaEntity> findAllByOwnerId(Long ownerId);
}
