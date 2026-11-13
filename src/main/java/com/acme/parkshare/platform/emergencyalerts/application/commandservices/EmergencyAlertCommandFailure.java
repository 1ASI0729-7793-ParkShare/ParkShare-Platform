package com.acme.parkshare.platform.emergencyalerts.application.commandservices;

public sealed interface EmergencyAlertCommandFailure permits
        EmergencyAlertCommandFailure.NotFound,
        EmergencyAlertCommandFailure.InvalidState {
    record NotFound() implements EmergencyAlertCommandFailure {}
    record InvalidState(String reason) implements EmergencyAlertCommandFailure {}
}
