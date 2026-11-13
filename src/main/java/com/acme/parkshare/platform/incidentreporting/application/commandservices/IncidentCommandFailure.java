package com.acme.parkshare.platform.incidentreporting.application.commandservices;
public sealed interface IncidentCommandFailure permits IncidentCommandFailure.NotFound, IncidentCommandFailure.InvalidStatus {
    record NotFound() implements IncidentCommandFailure {}
    record InvalidStatus(String reason) implements IncidentCommandFailure {}
}
