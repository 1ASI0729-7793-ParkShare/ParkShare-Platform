package com.acme.parkshare.platform.emergencyalerts.application.queryservices;

import com.acme.parkshare.platform.emergencyalerts.application.queries.GetAllEmergencyAlertsQuery;
import com.acme.parkshare.platform.emergencyalerts.application.queries.GetEmergencyAlertByIdQuery;
import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;

import java.util.List;
import java.util.Optional;

public interface EmergencyAlertQueryService {
    List<EmergencyAlert> handle(GetAllEmergencyAlertsQuery query);
    Optional<EmergencyAlert> handle(GetEmergencyAlertByIdQuery query);
}
