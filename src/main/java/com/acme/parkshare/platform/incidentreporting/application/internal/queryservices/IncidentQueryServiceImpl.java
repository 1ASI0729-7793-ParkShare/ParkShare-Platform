package com.acme.parkshare.platform.incidentreporting.application.internal.queryservices;

import com.acme.parkshare.platform.incidentreporting.application.queries.GetAllIncidentsQuery;
import com.acme.parkshare.platform.incidentreporting.application.queries.GetIncidentByIdQuery;
import com.acme.parkshare.platform.incidentreporting.application.queryservices.IncidentQueryService;
import com.acme.parkshare.platform.incidentreporting.domain.model.aggregates.Incident;
import com.acme.parkshare.platform.incidentreporting.domain.repositories.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class IncidentQueryServiceImpl implements IncidentQueryService {
    private final IncidentRepository repository;
    public IncidentQueryServiceImpl(IncidentRepository repository) { this.repository = repository; }
    @Override public List<Incident> handle(GetAllIncidentsQuery query) { return repository.findAll(); }
    @Override public Optional<Incident> handle(GetIncidentByIdQuery query) { return repository.findById(query.id()); }
}
