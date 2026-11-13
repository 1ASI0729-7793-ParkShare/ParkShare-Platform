package com.acme.parkshare.platform.incidentreporting.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.incidentreporting.infrastructure.persistence.jpa.entities.IncidentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IncidentJpaRepository extends JpaRepository<IncidentJpaEntity, Long> {
    List<IncidentJpaEntity> findAllByOwnerId(Long ownerId);
    List<IncidentJpaEntity> findAllByReporterId(Long reporterId);
}
