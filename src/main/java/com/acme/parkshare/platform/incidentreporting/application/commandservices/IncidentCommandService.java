package com.acme.parkshare.platform.incidentreporting.application.commandservices;

import com.acme.parkshare.platform.incidentreporting.application.commands.CreateIncidentCommand;
import com.acme.parkshare.platform.incidentreporting.application.commands.UpdateIncidentStatusCommand;
import com.acme.parkshare.platform.incidentreporting.domain.model.aggregates.Incident;
import com.acme.parkshare.platform.shared.application.result.Result;

public interface IncidentCommandService {
    Result<Incident, IncidentCommandFailure> handle(CreateIncidentCommand command);
    Result<Incident, IncidentCommandFailure> handle(UpdateIncidentStatusCommand command);
}
