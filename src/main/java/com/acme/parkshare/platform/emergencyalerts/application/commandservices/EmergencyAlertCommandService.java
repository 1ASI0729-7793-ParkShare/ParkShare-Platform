package com.acme.parkshare.platform.emergencyalerts.application.commandservices;

import com.acme.parkshare.platform.emergencyalerts.application.commands.CreateEmergencyAlertCommand;
import com.acme.parkshare.platform.emergencyalerts.application.commands.ResolveEmergencyAlertCommand;
import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;
import com.acme.parkshare.platform.shared.application.result.Result;

public interface EmergencyAlertCommandService {
    Result<EmergencyAlert, EmergencyAlertCommandFailure> handle(CreateEmergencyAlertCommand command);
    Result<EmergencyAlert, EmergencyAlertCommandFailure> handle(ResolveEmergencyAlertCommand command);
}
