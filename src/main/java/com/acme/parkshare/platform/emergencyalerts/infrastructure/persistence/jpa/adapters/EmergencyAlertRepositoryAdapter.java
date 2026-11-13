package com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;
import com.acme.parkshare.platform.emergencyalerts.domain.repositories.EmergencyAlertRepository;
import com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa.EmergencyAlertJpaRepository;
import com.acme.parkshare.platform.emergencyalerts.infrastructure.persistence.jpa.mappers.EmergencyAlertPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmergencyAlertRepositoryAdapter implements EmergencyAlertRepository {

    private final EmergencyAlertJpaRepository repository;
    private final EmergencyAlertPersistenceMapper mapper;

    public EmergencyAlertRepositoryAdapter(EmergencyAlertJpaRepository repository,
                                           EmergencyAlertPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EmergencyAlert save(EmergencyAlert alert) {
        return mapper.toDomain(repository.save(mapper.toEntity(alert)));
    }

    @Override
    public Optional<EmergencyAlert> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<EmergencyAlert> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<EmergencyAlert> findAllByOwnerId(Long ownerId) {
        return repository.findAllByOwnerId(ownerId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<EmergencyAlert> findAllByReporterId(Long reporterId) {
        return repository.findAllByReporterId(reporterId).stream().map(mapper::toDomain).toList();
    }
}
