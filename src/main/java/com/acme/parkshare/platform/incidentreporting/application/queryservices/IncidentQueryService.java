package com.acme.parkshare.platform.incidentreporting.application.queryservices;

import com.acme.parkshare.platform.incidentreporting.application.queries.GetAllIncidentsQuery;
import com.acme.parkshare.platform.incidentreporting.application.queries.GetIncidentByIdQuery;
import com.acme.parkshare.platform.incidentreporting.domain.model.aggregates.Incident;

import java.util.List;
import java.util.Optional;

public interface IncidentQueryService {
    List<Incident> handle(GetAllIncidentsQuery query);
    Optional<Incident> handle(GetIncidentByIdQuery query);
}
