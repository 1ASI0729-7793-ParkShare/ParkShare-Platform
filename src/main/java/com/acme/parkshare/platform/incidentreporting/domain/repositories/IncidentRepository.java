package com.acme.parkshare.platform.incidentreporting.domain.repositories;

import com.acme.parkshare.platform.incidentreporting.domain.model.aggregates.Incident;
import java.util.List;
import java.util.Optional;

public interface IncidentRepository {
    Incident save(Incident incident);
    Optional<Incident> findById(Long id);
    List<Incident> findAll();
    List<Incident> findAllByOwnerId(Long ownerId);
    List<Incident> findAllByReporterId(Long reporterId);
}
