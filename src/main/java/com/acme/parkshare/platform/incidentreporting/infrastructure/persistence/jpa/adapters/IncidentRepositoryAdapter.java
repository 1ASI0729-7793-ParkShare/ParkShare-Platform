package com.acme.parkshare.platform.incidentreporting.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.incidentreporting.domain.model.aggregates.Incident;
import com.acme.parkshare.platform.incidentreporting.domain.repositories.IncidentRepository;
import com.acme.parkshare.platform.incidentreporting.infrastructure.persistence.jpa.IncidentJpaRepository;
import com.acme.parkshare.platform.incidentreporting.infrastructure.persistence.jpa.mappers.IncidentPersistenceMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class IncidentRepositoryAdapter implements IncidentRepository {
    private final IncidentJpaRepository jpaRepository;
    private final IncidentPersistenceMapper mapper;
    public IncidentRepositoryAdapter(IncidentJpaRepository jpaRepository, IncidentPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository; this.mapper = mapper;
    }
    @Override public Incident save(Incident i) { return mapper.toDomain(jpaRepository.save(mapper.toEntity(i))); }
    @Override public Optional<Incident> findById(Long id) { return jpaRepository.findById(id).map(mapper::toDomain); }
    @Override public List<Incident> findAll() { return jpaRepository.findAll().stream().map(mapper::toDomain).toList(); }
    @Override public List<Incident> findAllByOwnerId(Long ownerId) { return jpaRepository.findAllByOwnerId(ownerId).stream().map(mapper::toDomain).toList(); }
    @Override public List<Incident> findAllByReporterId(Long rid) { return jpaRepository.findAllByReporterId(rid).stream().map(mapper::toDomain).toList(); }
}
