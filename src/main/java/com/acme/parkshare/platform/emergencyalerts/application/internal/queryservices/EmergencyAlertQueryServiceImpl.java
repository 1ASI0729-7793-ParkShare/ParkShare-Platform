package com.acme.parkshare.platform.emergencyalerts.application.internal.queryservices;

import com.acme.parkshare.platform.emergencyalerts.application.queries.GetAllEmergencyAlertsQuery;
import com.acme.parkshare.platform.emergencyalerts.application.queries.GetEmergencyAlertByIdQuery;
import com.acme.parkshare.platform.emergencyalerts.application.queryservices.EmergencyAlertQueryService;
import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;
import com.acme.parkshare.platform.emergencyalerts.domain.repositories.EmergencyAlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EmergencyAlertQueryServiceImpl implements EmergencyAlertQueryService {

    private final EmergencyAlertRepository repository;

    public EmergencyAlertQueryServiceImpl(EmergencyAlertRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EmergencyAlert> handle(GetAllEmergencyAlertsQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<EmergencyAlert> handle(GetEmergencyAlertByIdQuery query) {
        return repository.findById(query.id());
    }
}
